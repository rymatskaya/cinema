package senla.service;

import senla.model.Movie;
import senla.model.User;
import senla.repository.MovieRepository;
import senla.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {

      private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) { this.movieRepository = movieRepository;  }

    @Override
    public boolean createMovie(Movie movie) { return movieRepository.createMovie(movie);  }

    @Override
    public boolean checkMovieByTitle(String title) { return movieRepository.checkMovieByTitle(title);}

    @Override
    public Optional<Movie> getMovieById(Integer movieId) {return movieRepository.getMovieById(movieId);}
    @Override
    public List<Movie> getAllMovies()  {return movieRepository.getAllMovies();}
    @Override
    public boolean updateMovie(Integer movieId, String title) {return movieRepository.updateMovie(movieId, title);}
    @Override
    public boolean deleteMovie(Integer id) {return movieRepository.deleteMovie(id);}
    @Override
    public boolean checkMovieById(Integer Id) {return movieRepository.checkMovieById(Id);}
}
