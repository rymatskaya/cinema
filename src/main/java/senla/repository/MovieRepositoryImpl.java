package senla.repository;

import senla.model.Movie;
import senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepositoryImpl implements MovieRepository {
    @Override
    public boolean createMovie(Movie movie) {
        String title = movie.getTitle();
        boolean IsNotExistsMovie = checkMovieByTitle(title);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsMovie == false) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO movie (title) " +
                        "VALUES (?)");
                statement.setString(1, movie.getTitle());
                statement.execute();
                System.out.println("Фильм успешно добавлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Фильм существует %s ", title));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMovie(Integer movieId, String title) {

        boolean IsNotExistsMovie = checkMovieByTitle(title);

        try (Connection connection = ConnectionManager.open()) {
            if (IsNotExistsMovie == false) {
                PreparedStatement statement = connection.prepareStatement("UPDATE movie set Title=? where MovieId=?");
                statement.setString(1, title);
                statement.setString(2, String.valueOf(movieId));
                statement.executeUpdate();
                System.out.println("Фильм успешно обновлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Фильм не существует %s ", title));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkMovieByTitle(String title) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie WHERE Title like ?");
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Movie> getMovieById(Integer movieId) {
        Optional<Movie> movie;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie WHERE MovieId=?");
            statement.setInt(1, movieId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Movie entity = new Movie();
                entity.setMovieId(resultSet.getInt("MovieId"));
                entity.setTitle(resultSet.getString("Title"));
                movie = Optional.of(entity);
                return movie;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteMovie(Integer id) {

        boolean IsNotExistsMovie = checkMovieById(id);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsMovie == true) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM movie where MovieId=?");
                statement.setString(1, String.valueOf(id));
                statement.executeUpdate();
                System.out.println("Фильм успешно удален!");
                return true;
            } else
                throw new RuntimeException(String.format("Не удалось удалить фильм %s ", id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkMovieById(Integer Id) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie WHERE MovieId=?");
            statement.setString(1, String.valueOf(Id));
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String MovieId = resultSet.getString("MovieId");
                String Title = resultSet.getString("Title");
                Movie movie = new Movie(Integer.valueOf(MovieId), Title);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
