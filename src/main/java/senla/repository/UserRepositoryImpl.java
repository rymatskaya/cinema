package senla.repository;

import senla.model.User;
import senla.model.UserRole;
import senla.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static senla.util.SecurityUtil.passwordEncoder;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> getUserById(Integer userId) {
        Optional<User> user;

        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User entity = new User();
                entity.setId(resultSet.getInt("id"));
                entity.setUsername(resultSet.getString("username"));
                entity.setPassword(resultSet.getString("password"));
                entity.setPassword(resultSet.getString("email"));
                entity.setPassword(resultSet.getString("role"));
                user = Optional.of(entity);
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByLoginPassword(String username, String password) {
        Optional<User> user;

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE " +
                    "username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                if (passwordEncoder.matches(password, resultSet.getString("password"))) {
                    User entity = new User();
                    entity.setId(resultSet.getInt("id"));
                    entity.setUsername(resultSet.getString("username"));
                    entity.setPassword(resultSet.getString("password"));
                    entity.setRole(UserRole.valueOf(resultSet.getString("role")));
                    user = Optional.of(entity);
                    return user;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");
                User user = new User(username, password, email, role);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean checkUserByLogin(String username) {

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE " +
                    "username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
            }

            if (i != 0) {
                System.out.printf("Пользователь с именем %s существует!", username);
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkUserById(Integer Id) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE " +
                    "Id=?");
            statement.setString(1, String.valueOf(Id));
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                i++;
            }
            if (i != 0) {
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean checkUserByLoginPassword(String username, String password) {

        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE " +
                    "username=? and password=?");
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (passwordEncoder.matches(password, resultSet.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean create(User user) {
        String username = user.getUsername();
        boolean IsNotExistsUser = checkUserByLogin(username);
        //System.out.println("IsNotExistsUser=" + IsNotExistsUser);
       try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsUser == false) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO person (username, password, email, role) " +
                        "VALUES (?,?,?,?)");
                statement.setString(1, user.getUsername());
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                statement.setString(2, encodedPassword);
                statement.setString(3, user.getEmail());
                statement.setString(4, UserRole.USER.name());
                statement.execute();
                System.out.println("Пользователь успешно добавлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Пользователь существует %s ", username));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createByAdmin(User user) {
        String username = user.getUsername();
        boolean IsNotExistsUser = checkUserByLogin(username);
        System.out.println("IsNotExistsUser=" + IsNotExistsUser);

        try (Connection connection = ConnectionManager.open()) {

            //   PreparedStatement statement = connection.prepareStatement("INSERT INTO person (username, password, email) " +
            //           "VALUES ('Alex','1221','emailTest@mail.com')");
            if (IsNotExistsUser == false) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO person (username, password, email, role) " +
                        "VALUES (?,?,?,?)");
                statement.setString(1, user.getUsername());
                //statement.setString(2, user.getPassword());
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                statement.setString(2, encodedPassword);
                statement.setString(3, user.getEmail());
                statement.setString(4, String.valueOf(user.getRole()));
                statement.execute();
                System.out.println("Пользователь успешно добавлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Пользователь существует %s ", username));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateByAdmin(Integer id, String username, String password, String email, UserRole role) {

        boolean IsNotExistsUser = checkUserById(id);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsUser == true) {
                PreparedStatement statement = connection.prepareStatement("UPDATE person set username=?, " +
                        "password=?, email=?, role=? where id=? ");

                statement.setString(1, username);
                String encodedPassword = passwordEncoder.encode(password);
                statement.setString(2, encodedPassword);
                //statement.setString(2, password);
                statement.setString(3, email);
                try {
                    statement.setString(4, String.valueOf(role));
                } catch (Exception e){
                    new Exception("Неверно введена роль");
                }

                statement.setString(5, String.valueOf(id));
                statement.executeUpdate();
                System.out.println("Пользователь успешно обновлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Не удалось обновить пользователя %s ", username));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByAdmin(Integer id) {

        boolean IsNotExistsUser = checkUserById(id);

        try (Connection connection = ConnectionManager.open()) {

            if (IsNotExistsUser == true) {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM person " +
                        " where id=? ");
                statement.setString(1, String.valueOf(id));
                statement.executeUpdate();
                System.out.println("Пользователь успешно обновлен!");
                return true;
            } else
                throw new RuntimeException(String.format("Не удалось удалить пользователя %s ", id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
