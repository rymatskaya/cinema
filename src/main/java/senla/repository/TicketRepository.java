package senla.repository;

import senla.model.Ticket;
import senla.model.TicketList;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {

    boolean createTicket(Ticket ticket);

    boolean deleteTicket(Integer ticketId);

    boolean checkTicketByEventAndPlace(Integer idEvent, String place);

    boolean updateTicket(Integer ticketId, Integer eventId, String place, Double price);

    Optional<Ticket> getTicketById(Integer ticketId);
    Ticket getTicketByPlaceAndEvent(String place, Integer eventId);

    boolean buyTicket(Integer eventId, String place, Integer userId);
    boolean returnTicket(Integer eventId, String place, Integer userId);

    boolean checkTicketBySold(Integer Id);
    boolean checkTicketById(Integer Id);
    List<TicketList> getTicketsBySold(Integer idEvent);
    List<Ticket> getAllTickets();
    List<TicketList> getUserTickets(Integer userId);
}