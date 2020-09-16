package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Service method to update or create a user
     * @param user user data to update
     * @return User object, or null if not found (i.e. when updating via username)
     */
    public User saveOrUpdateUser(User user) {
        Long userId = user.getId();
        if (userId == null) {  // if ID is null we should check for username
            String username = user.getUsername();
            if (username != null) {  // append id to user parameter
                Optional<User> user1 = userRepository.findByUsername(username);
                user1.ifPresent(value -> user.setId(value.getId()));
            } else {  // if we cannot find by username then the request was incorrect
                return null;
            }
        }
        return userRepository.save(user);
    }

    /**
     * Returns a list of all workers
     * @return a Collection of workers (ArrayList)
     */
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Return a user based on their ID
     * @param id Long id of user (generated value)
     * @return User object, or null if not found
     */
    public User findById(Long id) {
        Optional<User> customer = userRepository.findById(id);
        return customer.orElse(null);
    }

    /**
     * Return a user based on username
     * @param username: unique username of user
     * @return User object of null if not found
     */
    public User findByUsername(String username) {
        Optional<User> customer = userRepository.findByUsername(username);
        return customer.orElse(null);
    }

    /**
     * Authenticate a user - check ID and password
     * @param username: id of user to fetch
     * @param password: password to check
     * @return User object or null if not found
     */
    public User authenticateUser(String username, String password) {
        User user = findByUsername(username);
        return (user.getPassword().compareTo(password) == 0) ? user : null;
    }

}
