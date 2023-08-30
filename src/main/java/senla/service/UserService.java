package senla.service;

import senla.model.User;
import senla.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean create(User user) ;
    boolean createByAdmin(User user) ;
    boolean updateByAdmin(Integer id, String username, String password, String email, UserRole role) ;
    boolean deleteByAdmin(Integer id);
    Optional<User> getUserById(Integer userId) ;

    Optional<User> getUserByLoginPassword(String username, String password) ;

    List<User> getAll() ;
    boolean checkUserByLogin(String username);

    boolean checkUserByLoginPassword(String username, String password);
    boolean checkUserById(Integer Id);

}
