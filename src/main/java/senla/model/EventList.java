package senla.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventList {
    private Integer eventId;
    private String movie;
    private LocalDateTime movieDateTime;

    public EventList(Integer eventId, String  movie, LocalDateTime movieDateTime) {
        this.eventId = eventId;
        this.movie = movie;
        this.movieDateTime = movieDateTime;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public LocalDateTime getMovieDateTime() {
        return movieDateTime;
    }

    public void setMovieDateTime(LocalDateTime movieDateTime) {
        this.movieDateTime = movieDateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return  "Код сеанса " + eventId +
                ", фильм= " + movie +
                ", дата и время =" + movieDateTime.format(formatter) ;
    }
}
