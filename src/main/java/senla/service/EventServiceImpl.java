package senla.service;

import senla.model.Event;
import senla.model.EventList;
import senla.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements  EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) { this.eventRepository = eventRepository;  }

    @Override
    public boolean createEvent(Event event) { return eventRepository.createEvent(event);  }

    @Override
    public Optional<Event> getEventById(Integer eventId) {return eventRepository.getEventById(eventId);}
    @Override
    public List<Event> getAllEvents()  {return eventRepository.getAllEvents();}
    @Override
    public List<EventList> getAllEventsFull() {return eventRepository.getAllEventsFull();}
    @Override
    public boolean updateEvent(Integer evenId, Integer movieId, LocalDateTime movieDateTime) {
        return eventRepository.updateEvent(evenId, movieId, movieDateTime);
    }
    @Override
    public boolean deleteEvent(Integer id) {return eventRepository.deleteEvent(id);}
    @Override
    public boolean checkEventById(Integer Id) {return eventRepository.checkEventById(Id);}
    @Override
    public boolean checkEventByMovieAndTime(Integer idMovie, LocalDateTime eventDateTime) {
        return  eventRepository.checkEventByMovieAndTime(idMovie, eventDateTime);
    };
   }
