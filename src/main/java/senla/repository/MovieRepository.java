package senla.repository;

import senla.model.Movie;
import senla.model.User;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    boolean createMovie(Movie movie);
    boolean updateMovie(Integer movieId, String title);
    boolean deleteMovie(Integer id) ;
    boolean checkMovieById(Integer Id);
    boolean checkMovieByTitle(String title);

    Optional<Movie> getMovieById(Integer movieId);
    List<Movie> getAllMovies();
}