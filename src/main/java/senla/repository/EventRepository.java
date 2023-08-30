package senla.repository;

import senla.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository {
    boolean createEvent(Event event) ;
    boolean updateEvent(Integer evenId, Integer movieId, LocalDateTime movieDateTime);
    boolean deleteEvent(Integer id) ;
    boolean checkEventById(Integer Id);
    boolean checkEventByMovieAndTime(Integer idMovie, LocalDateTime eventDateTime);
    Optional<Event> getEventById(Integer eventId);
    List<Event> getAllEvents() ;
}
