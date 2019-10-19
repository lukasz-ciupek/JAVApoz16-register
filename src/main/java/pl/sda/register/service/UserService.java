package pl.sda.register.service;

import org.springframework.stereotype.Service;
import pl.sda.register.model.User;
import pl.sda.register.repository.UserRepository;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Set<String> findAllUserNames(String firstName, boolean exactMatch) {

        return userRepository.findAllUserNames(firstName,exactMatch);
    }

    public User findUserByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void addUser(User user){
        userRepository.addUser(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}
