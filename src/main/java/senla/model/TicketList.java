package senla.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketList {

    private Integer eventId;
    private Integer userId;
    private String movie;
    private String place;
    private Double price;
    private LocalDateTime movieDateTime;

    public TicketList(Integer eventId, Integer userId, String movie, String place, Double price, LocalDateTime movieDateTime) {
        this.eventId = eventId;
        this.userId = userId;
        this.movie = movie;
        this.place = place;
        this.price = price;
        this.movieDateTime = movieDateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return  " Фильм = " + movie +
                ", место = " + place +
                ", цена = " + price +
                ", дата и время = " + movieDateTime.format(formatter)     ;
    }
}
