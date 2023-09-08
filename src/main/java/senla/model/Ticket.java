package senla.model;

public class Ticket {
    private Integer ticketId;
    private Integer userId;
    private Integer eventId;
    private Double price;
    private int sold;

    private String place;

    public Ticket(Integer userId, Integer eventId, Double price, String place, int sold) {
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

    public int isSold() {      return sold;   }

    public void setSold(int sold) {
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
        return "Билет "+ ticketId + ":" +
                " код пользователя = " + userId +
                ", код сеанса = " + eventId +
                ", цена = " + price +
                ", продано = " + sold +
                ", место = " + place;
    }

    public Ticket() {
    }

    public Ticket(Integer ticketId, Integer userId, Integer eventId, Double price, int sold, String place) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.eventId = eventId;
        this.price = price;
        this.sold = sold;
        this.place = place;
    }
}
