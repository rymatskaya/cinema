package senla.service;

import senla.model.User;
import senla.model.UserRole;
import senla.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public boolean create(User user) {
        return userRepository.create(user);
    }
    @Override
    public boolean  createByAdmin(User user)  {
        return userRepository.createByAdmin(user);
    }
    @Override
    public boolean  updateByAdmin(Integer id, String username, String password, String email, UserRole role)  {
        return userRepository.updateByAdmin(id, username, password, email, role);
    }
    @Override
    public boolean deleteByAdmin(Integer id) {
        return userRepository.deleteByAdmin(id);
    };
    @Override
    public Optional<User> getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public Optional<User> getUserByLoginPassword(String username, String password) {
        return userRepository.getUserByLoginPassword(username, password);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public boolean checkUserByLogin(String username) {
        return userRepository.checkUserByLogin(username);
    }
    @Override
    public boolean checkUserById(Integer id) {
        return userRepository.checkUserById(id);
    }

    @Override
    public boolean checkUserByLoginPassword(String username, String password) {
        return userRepository.checkUserByLoginPassword(username, password);
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
