package senla.repository;

import senla.model.Ticket;
import senla.model.TicketList;
import senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public boolean createTicket(Ticket ticket) {
        String place = ticket.getPlace();
        Integer eventId = ticket.getEventId();
        boolean IsNotExistsTicket = checkTicketByEventAndPlace(eventId, place);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsTicket == false) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO ticket (userId, eventId, price, " +
                        "sold, place) VALUES (?,?,?,?,?)");
                statement.setString(1, String.valueOf(ticket.getUserId()));
                statement.setString(2, String.valueOf(ticket.getEventId()));
                statement.setString(3, String.valueOf(ticket.getPrice()));
                statement.setString(4, String.valueOf(ticket.isSold()));
                statement.setString(5, String.valueOf(ticket.getPlace()));
                statement.execute();
                System.out.println("Билет успешно добавлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Билет с таким событием и местом %s существует", place));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkTicketByEventAndPlace(Integer idEvent, String place) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE eventId=? " +
                    "and place=?");
            statement.setString(1, String.valueOf(idEvent));
            statement.setString(2, String.valueOf(place));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateTicket(Integer ticketId, Integer eventId, String place, Double price) {

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE ticket set eventId=?, " +
                    "place=?, price=?  where ticketId=?");
            statement.setString(1, String.valueOf(eventId));
            statement.setString(2, place);
            statement.setString(3, String.valueOf(price));
            statement.setString(4, String.valueOf(ticketId));
            statement.executeUpdate();
            System.out.println("Билет успешно обновлен!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean buyTicket(Integer eventId, String place, Integer userId) {
        boolean IsNotExistsTicket = checkTicketByEventAndPlace(eventId, place);
        Ticket ticket = getTicketByPlaceAndEvent(place, eventId);

        boolean IsNotSoldTicket = checkTicketBySold(ticket.getTicketId());

        try (Connection connection = ConnectionManager.open()) {
            if (IsNotExistsTicket == true && IsNotSoldTicket == false) {
                PreparedStatement statement = connection.prepareStatement("UPDATE ticket set  " +
                        "sold=1, userId=?  where ticketId=?");
                statement.setString(1, String.valueOf(userId));
                statement.setString(2, String.valueOf(ticket.getTicketId()));
                statement.executeUpdate();
                System.out.println("Билет куплен!");
                return true;
            } else
                throw new RuntimeException(String.format("Что-то пошло не так. Билет с местом %s не куплен",
                        place));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean returnTicket(Integer eventId, String place, Integer userId) {
        boolean IsNotExistsTicket = checkTicketByEventAndPlace(eventId, place);
        Ticket ticket = getTicketByPlaceAndEvent(place, eventId);

        boolean IsNotSoldTicket = checkTicketBySold(ticket.getTicketId());

        try (Connection connection = ConnectionManager.open()) {
            if (IsNotExistsTicket == true && IsNotSoldTicket == true) {
                PreparedStatement statement = connection.prepareStatement("UPDATE ticket set  " +
                        "sold=0, userId=?  where ticketId=? and userId=?");
                statement.setString(1, null);
                statement.setString(2, String.valueOf(ticket.getTicketId()));
                statement.setString(3, String.valueOf(userId));
                statement.executeUpdate();
                System.out.println("Билет возвращен!");
                return true;
            } else
                throw new RuntimeException(String.format("Что-то пошло не так. Билет с местом %s не куплен",
                        place));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkTicketBySold(Integer Id) {

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE ticketId=?" +
                    " AND sold=1");
            statement.setString(1, String.valueOf(Id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Optional<Ticket> getTicketById(Integer ticketId) {
        Optional<Ticket> ticket;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE ticketId=?");
            statement.setInt(1, ticketId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Ticket entity = new Ticket();
                entity.setTicketId(resultSet.getInt("ticketId"));
                entity.setEventId(resultSet.getInt("eventId"));
                entity.setUserId(resultSet.getInt("userId"));
                entity.setPrice(resultSet.getDouble("price"));
                entity.setPlace(resultSet.getString("place"));
                entity.setSold(resultSet.getInt("sold"));
                ticket = Optional.of(entity);
                return ticket;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Ticket getTicketByPlaceAndEvent(String place, Integer eventId) {
        Ticket ticket;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE eventId=?" +
                    " AND UPPER(place) like UPPER(?)");
            statement.setInt(1, eventId);
            statement.setString(2, place);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Ticket entity = new Ticket();
                entity.setTicketId(resultSet.getInt("ticketId"));
                entity.setEventId(resultSet.getInt("eventId"));
                entity.setUserId(resultSet.getInt("userId"));
                entity.setPrice(resultSet.getDouble("price"));
                entity.setPlace(resultSet.getString("place"));
                entity.setSold(resultSet.getInt("sold"));
                ticket = entity;
                return ticket;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteTicket(Integer id) {
        boolean IsNotExistsTicket = checkTicketById(id);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsTicket == true) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM ticket where ticketId=?");
                statement.setString(1, String.valueOf(id));
                statement.executeUpdate();
                System.out.println("Билет успешно удален!");
                return true;
            } else
                throw new RuntimeException(String.format("Не удалось удалить билет с id=%s ", id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkTicketById(Integer Id) {

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE ticketId=?");
            statement.setString(1, String.valueOf(Id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String TicketId = resultSet.getString("ticketId");
                String userId = resultSet.getString("userId");
                String eventId = resultSet.getString("eventId");
                String price = resultSet.getString("price");
                String sold = resultSet.getString("sold");
                String place = resultSet.getString("place");
                Ticket ticket = new Ticket(Integer.valueOf(TicketId), Integer.parseInt(userId), Integer.valueOf(eventId),
                        Double.valueOf(price), Integer.parseInt(sold), place);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TicketList> getUserTickets(Integer userId) {
        List<TicketList> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT event.MovieDateTime, " +
                    "movie.Title, ticket.place, ticket.price, ticket.ticketId, ticket.eventId FROM event " +
                    "inner join ticket on event.eventId=ticket.eventId " +
                    "inner join movie on event.movieId=movie.movieId " +
                    "where userId=?");
            statement.setString(1, String.valueOf(userId));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String TicketId = resultSet.getString("ticketId");
                Integer idUser = userId;
                String eventId = resultSet.getString("eventId");
                String price = resultSet.getString("price");
                String sold = String.valueOf(1);
                String movie = resultSet.getString("Title");
                ;
                String place = resultSet.getString("place");
                String MovieDateTime = resultSet.getString("MovieDateTime");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parse = LocalDateTime.parse(MovieDateTime, formatter);
                TicketList ticket = new TicketList(Integer.valueOf(eventId), userId, movie, place,
                        Double.valueOf(price), parse);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TicketList> getTicketsBySold(Integer idEvent) {
        List<TicketList> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT event.MovieDateTime, " +
                    "movie.Title, ticket.place, ticket.price, ticket.ticketId, ticket.eventId FROM event " +
                    "inner join ticket on event.eventId=ticket.eventId " +
                    "inner join movie on event.movieId=movie.movieId " +
                    "where sold=0 and ticket.eventId=?");
            statement.setString(1, String.valueOf(idEvent));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String TicketId = resultSet.getString("ticketId");
                Integer idUser = Integer.valueOf(resultSet.getString("ticketId"));
                String eventId = resultSet.getString("eventId");
                String price = resultSet.getString("price");
                String sold = String.valueOf(1);
                String movie = resultSet.getString("Title");
                String place = resultSet.getString("place");
                String MovieDateTime = resultSet.getString("MovieDateTime");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime parse = LocalDateTime.parse(MovieDateTime, formatter);
                TicketList ticket = new TicketList(Integer.valueOf(eventId), idUser, movie, place,
                        Double.valueOf(price), parse);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
