package senla.repository;

import senla.model.Event;
import senla.model.Movie;
import senla.model.Ticket;
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

public class TicketRepositoryImpl implements TicketRepository{

    @Override
    public boolean createTicket(Ticket ticket) {
        String place = ticket.getPlace();
        Integer eventId = ticket.getEventId();
        boolean IsNotExistsTicket = checkTicketByEventAndPlace(eventId, place);
        System.out.println("IsNotExistsTicket=" + IsNotExistsTicket);

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

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM ticket WHERE userId=? " +
                    "and place=?");
            statement.setString(1, String.valueOf(idEvent));
            statement.setString(2, String.valueOf(place));
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
            }
            System.out.println("i=" + i);
            if (i != 0) {
                System.out.printf("Билет с таким местом %s на этот сеанс  существует!", place);
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateTicket(Integer ticketId, Integer eventId, String place, Double price) {
        boolean IsNotExistsTicket = checkTicketByEventAndPlace(eventId, place);
        System.out.println("IsNotExistsTicket=" + IsNotExistsTicket);

        try (Connection connection = ConnectionManager.open()) {
            if (IsNotExistsTicket == true) {
                PreparedStatement statement = connection.prepareStatement("UPDATE ticket set eventId=?, " +
                        "place=?, price=?  where ticketId=?");
                statement.setString(1, String.valueOf(eventId));
                statement.setString(2, place);
                statement.setString(3, String.valueOf(price));
                statement.setString(3, String.valueOf(ticketId));
                statement.executeUpdate();
                System.out.println("Билет успешно обновлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Билет с таким местом %s по данному событию не существует",
                        place));
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
                entity.setSold(resultSet.getBoolean("sold"));
                ticket = Optional.of(entity);
                return ticket;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
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
            int i = 0;
            while (resultSet.next()) {
                i++;
            }
            if (i != 0) {
                return true;
            } else return false;
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
                Ticket ticket = new Ticket(Integer.valueOf(TicketId), Integer.valueOf(userId), Integer.valueOf(eventId),
                                            Double.valueOf(price), Boolean.valueOf(sold),place);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
