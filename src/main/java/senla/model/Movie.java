package senla.model;

public class Movie {
    private Integer movieId;
    private String title;

    public Movie() {
    }

    public Movie(Integer movieId, String title) {
        this.movieId = movieId;
        this.title = title;
    }

    public Movie(String title) {
          this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Фильм: " +
                "movieId=" + movieId +
                ", название фильма='" + title + '\'' ;
    }
}
