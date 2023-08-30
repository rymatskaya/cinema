package senla.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Event {
    private Integer eventId;
    private Integer movieId;
    private LocalDateTime movieDateTime;

    public Event(Integer eventId, Integer movieId, LocalDateTime movieDateTime) {
        this.eventId = eventId;
        this.movieId = movieId;
        this.movieDateTime = movieDateTime;
    }

    public Event() {
    }

    public Event(Integer movieId, LocalDateTime movieDateTime) {
        this.movieId = movieId;
        this.movieDateTime = movieDateTime;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovie(Integer movie) {
        this.movieId = movieId;
    }

    public LocalDateTime getMovieDateTime() {
        return movieDateTime;
    }

    public void setMovieDateTime(LocalDateTime movieDateTime) {
        this.movieDateTime = movieDateTime;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "Сеанс: " +
                " Id сеанса=" + eventId +
                ", Id фильма=" + movieId +
                ", Дата и время сеанса= " + movieDateTime.format(formatter);
    }
}
