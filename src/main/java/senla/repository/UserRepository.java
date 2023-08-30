package senla.repository;

import senla.model.User;
import senla.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository  {

    boolean create(User user) ;

    boolean createByAdmin(User user) ;
    boolean updateByAdmin(Integer id, String username, String password, String email, UserRole role) ;
    boolean deleteByAdmin(Integer id) ;
    boolean checkUserByLogin(String username) ;
    boolean checkUserById(Integer Id) ;
    boolean checkUserByLoginPassword(String username, String password);
    Optional<User> getUserById(Integer userId) ;
    Optional<User> getUserByLoginPassword(String username, String password);
    List<User> getAll() ;
}
