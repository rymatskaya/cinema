package senla.service;

import senla.model.Ticket;
import senla.model.TicketList;
import senla.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

public class TicketServiceImpl implements TicketService{
    private TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) { this.ticketRepository = ticketRepository;  }

    @Override
    public boolean createTicket(Ticket ticket) { return ticketRepository.createTicket(ticket);  }
    @Override
    public boolean checkTicketByEventAndPlace(Integer idEvent, String place) {
        return ticketRepository.checkTicketByEventAndPlace(idEvent,place);
    }
    @Override
    public boolean updateTicket(Integer ticketId, Integer eventId, String place, Double price) {
        return ticketRepository.updateTicket(ticketId, eventId, place, price);
    }
    @Override
    public boolean buyTicket(Integer eventId, String place, Integer userId) {
        return ticketRepository.buyTicket(eventId, place, userId);
    }
    @Override
    public boolean returnTicket(Integer eventId, String place, Integer userId) {
        return ticketRepository.returnTicket(eventId, place, userId);
    }
    @Override
    public Optional<Ticket> getTicketById(Integer ticketId) {
        return ticketRepository.getTicketById(ticketId);
    }
    @Override
    public Ticket getTicketByPlaceAndEvent(String place, Integer eventId) {
        return ticketRepository.getTicketByPlaceAndEvent(place, eventId);
    }
    @Override
    public boolean checkTicketBySold(Integer Id) {return ticketRepository.checkTicketBySold(Id);}
    @Override
    public boolean deleteTicket(Integer ticketId) {
        return ticketRepository.deleteTicket(ticketId);
    }
    @Override
    public boolean checkTicketById(Integer Id) {
        return ticketRepository.checkTicketById(Id);
    }
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.getAllTickets();
    }
    @Override
    public List<TicketList> getUserTickets(Integer userId)  {
        return ticketRepository.getUserTickets(userId);
    }
}
