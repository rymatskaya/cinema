package senla.service;

import senla.model.Event;
import senla.model.Movie;
import senla.model.Ticket;
import senla.repository.MovieRepository;
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
    public Optional<Ticket> getTicketById(Integer ticketId) {
        return ticketRepository.getTicketById(ticketId);
    }
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
}
