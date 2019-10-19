package pl.sda.register.repository;

import org.springframework.stereotype.Repository;
import pl.sda.register.exception.DuplicatedUsernameException;
import pl.sda.register.exception.UserNotFoundException;
import pl.sda.register.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private Set<User> users = initializeUsers();

    private Set<User> initializeUsers() {
        return new HashSet<>(Arrays.asList(new User("login", "Captain", "Jack")));
    }

    public Set<String> findAllUserNames(String firstName, boolean exactMatch) {
        if (firstName == null) {
            return users.stream().map(User::getUsername).collect(Collectors.toSet());
        }

        if (exactMatch) {
            return users.stream()
                    .filter(user -> user.getFirstName().equals(firstName))
                    .map(User::getUsername)
                    .collect(Collectors.toSet());
        }

            return users.stream()
                    .filter(user -> user.getFirstName().contains(firstName))
                    .map(User::getUsername)
                    .collect(Collectors.toSet());
    }

    public User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found"));
    }

    public void addUser(User user)
    {
        users.stream()
            .filter(userInDatabase -> user.getUsername().equals(userInDatabase.getUsername()))
            .findAny()
            .ifPresent(foundUser -> {
                throw new DuplicatedUsernameException("User with username: " + foundUser.getUsername() + " exists.");
            });
        users.add(user);
    }

    public void deleteUser(String username) {
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny();
        if (userOptional.isPresent())
        {
            users.remove(userOptional.get());
        }
        else
            throw new UserNotFoundException("User with username: " + username + " not found");
    }

    public void updateUser(User user) {
        users.remove(user);
        users.add(user);
    }
}
