package senla.service;

import senla.model.Event;
import senla.model.Movie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {
    boolean createEvent(Event event) ;
    boolean updateEvent(Integer evenId, Integer movieId, LocalDateTime movieDateTime);
    boolean deleteEvent(Integer id) ;
    boolean checkEventById(Integer Id);
    boolean checkEventByMovieAndTime(Integer idMovie, LocalDateTime eventDateTime);
    Optional<Event> getEventById(Integer eventId);
    List<Event> getAllEvents() ;
}
