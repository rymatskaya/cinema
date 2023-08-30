package senla.repository;

import senla.model.Event;
import senla.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    boolean createTicket(Ticket ticket);

    boolean deleteTicket(Integer ticketId);

    boolean checkTicketByEventAndPlace(Integer idEvent, String place);

    boolean updateTicket(Integer ticketId, Integer eventId, String place, Double price);

    Optional<Ticket> getTicketById(Integer ticketId);

    boolean checkTicketById(Integer Id);

    List<Ticket> getAllTickets();
}