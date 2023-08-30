package senla.repository;

import senla.model.Event;
import senla.model.Movie;
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

public class EventRepositoryImpl implements EventRepository {
    @Override
    public boolean createEvent(Event event) {
        Integer idMovie = event.getMovieId();
        LocalDateTime eventDateTime = event.getMovieDateTime();
        boolean IsNotExistsEvent = checkEventByMovieAndTime(idMovie, eventDateTime);
        System.out.println("IsNotExistsEvent=" + IsNotExistsEvent);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsEvent == false) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO event (movieId, MovieDateTime) " +
                        "VALUES (?,?)");
                statement.setString(1, String.valueOf(event.getMovieId()));
                statement.setString(2, String.valueOf(event.getMovieDateTime()));
                statement.execute();
                System.out.println("Событие успешно добавлено!");
                return true;
            } else
                throw new RuntimeException(String.format("Событие с таким фильмом и временем %s существует", eventDateTime));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEvent(Integer evenId, Integer movieId, LocalDateTime movieDateTime) {
        boolean IsNotExistsEvent = checkEventById(evenId);
        System.out.println("IsNotExistsEvent=" + IsNotExistsEvent);

        try (Connection connection = ConnectionManager.open()) {
            if (IsNotExistsEvent == true) {
                PreparedStatement statement = connection.prepareStatement("UPDATE event set movieId=?," +
                        "MovieDateTime=? where eventId=?");
                statement.setString(1, String.valueOf(movieId));
                statement.setString(2, String.valueOf(movieDateTime));
                statement.setString(3, String.valueOf(evenId));
                statement.executeUpdate();
                System.out.println("Событие успешно обновлено!");
                return true;
            } else
                throw new RuntimeException(String.format("Событие с таким фильмом и временем %s не существует",
                        movieDateTime));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEvent(Integer id) {
        boolean IsNotExistsEvent = checkEventById(id);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsEvent == true) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM event where eventId=?");
                statement.setString(1, String.valueOf(id));
                statement.executeUpdate();
                System.out.println("Сеанс успешно удален!");
                return true;
            } else
                throw new RuntimeException(String.format("Не удалось удалить сеанс с id=%s ", id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkEventById(Integer Id) {

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM event WHERE eventId=?");
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
    public boolean checkEventByMovieAndTime(Integer idMovie, LocalDateTime eventDateTime) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM event WHERE movieId=? " +
                    "and MovieDateTime=?");
            statement.setString(1, String.valueOf(idMovie));
            statement.setString(2, String.valueOf(eventDateTime));
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
            }
            System.out.println("i=" + i);
            if (i != 0) {
                System.out.printf("Событие с таким фильмом и временем %s существует!", eventDateTime);
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Event> getEventById(Integer eventId) {
        Optional<Event> event;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM event WHERE eventId=?");
            statement.setInt(1, eventId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Event entity = new Event();
                entity.setEventId(resultSet.getInt("eventId"));
                entity.setMovie(resultSet.getInt("movieId"));
                entity.setMovieDateTime(resultSet.getTimestamp("MovieDateTime").toLocalDateTime());
                event = Optional.of(entity);
                return event;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM event");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String EventId = resultSet.getString("EventId");
                String MovieId = resultSet.getString("MovieId");
                String MovieDateTime = resultSet.getString("MovieDateTime");
                DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime parse = LocalDateTime.parse(MovieDateTime, formatter);
                Event event = new Event(Integer.valueOf(EventId), Integer.valueOf(MovieId), parse);
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    boolean updateEvent(Integer evenId, Integer movieId, LocalDateTime movieDateTime);
//    boolean deleteEvent(Integer id) ;
//    boolean checkEventById(Integer Id);
//    Optional<Event> getEventById(Integer eventId);
//    List<Event> getAllEvents() ;
}
