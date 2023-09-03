package senla.service;

import senla.model.Event;
import senla.model.Ticket;
import senla.model.TicketList;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    boolean createTicket(Ticket ticket) ;
    boolean checkTicketByEventAndPlace(Integer idEvent, String place);

    boolean updateTicket(Integer ticketId, Integer eventId, String place, Double price);
    Optional<Ticket> getTicketById(Integer ticketId);
    Ticket getTicketByPlaceAndEvent(String place, Integer eventId);
    boolean checkTicketBySold(Integer Id);
    boolean deleteTicket(Integer ticketId);
    boolean checkTicketById(Integer Id);
    List<Ticket> getAllTickets();
    boolean buyTicket(Integer eventId, String place, Integer userId);
    boolean returnTicket(Integer eventId, String place, Integer userId);
    List<TicketList> getUserTickets(Integer userId);
}
