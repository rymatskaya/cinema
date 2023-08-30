package senla.service;

import senla.model.Event;
import senla.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    boolean createTicket(Ticket ticket) ;
    boolean checkTicketByEventAndPlace(Integer idEvent, String place);

    boolean updateTicket(Integer ticketId, Integer eventId, String place, Double price);
    Optional<Ticket> getTicketById(Integer ticketId);

    boolean deleteTicket(Integer ticketId);
    boolean checkTicketById(Integer Id);
    List<Ticket> getAllTickets();
}
