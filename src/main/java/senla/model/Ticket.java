package senla.model;

import java.util.UUID;

public class Ticket {
    private Integer ticketId;
    private Integer userId;
    private Integer eventId;
    private Double price;
    private boolean sold;

    private String place;

    public Ticket(Integer userId, Integer eventId, Double price, String place, boolean sold) {
        this.userId = userId;
        this.eventId = eventId;
        this.price = price;
        this.sold = sold;
        this.place = place;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Ticket: " +
                "userId=" + userId +
                ", eventId=" + eventId +
                ", price=" + price +
                ", sold=" + sold +
                '}';
    }

    public Ticket() {
    }

    public Ticket(Integer ticketId, Integer userId, Integer eventId, Double price, boolean sold, String place) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.eventId = eventId;
        this.price = price;
        this.sold = sold;
        this.place = place;
    }
}
