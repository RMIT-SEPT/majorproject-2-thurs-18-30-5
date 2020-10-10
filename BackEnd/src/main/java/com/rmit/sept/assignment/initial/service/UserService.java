package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * User Service acts as an intermediary between the Repo and Controller classes, providing validation and handling
 * of requests.
 */
@Service
public class UserService implements UserDetailsService {
    private final BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    public UserService(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Service method to update or create a user
     * @param user user data to update
     * @return User object, or null if not found (i.e. when updating via username)
     */
    public User saveOrUpdateUser(User user, boolean create) {
        Long userId = user.getId();
        if (userId == null) {  // if ID is null we should check for username
            String username = user.getUsername();
            if (username != null) {  // append id to user parameter
                Optional<User> user1 = userRepository.findByUsername(username);
                if (create && user1.isPresent()) return null;  // return null if duplicate username and creating a user
                user1.ifPresent(value -> user.setId(value.getId()));
            } else {  // if we cannot find by username then the request was incorrect
                return null;
            }
        }
        String password = user.getPassword();
        if (password != null) {
            user.setPassword(encoder.encode(user.getPassword()));
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
     * Authenticate a user - check username and password
     * @param username: username of user to fetch
     * @param password: password to check
     * @return User object or null if not found
     */
    public User authenticateUser(String username, String password) {
        User user = findByUsername(username);
        if (user != null)
            if (encoder.matches(password, user.getPassword()))
                return user;
        return null;
    }

    /**
     * Authenticate a user - check ID and password
     * @param id: id of user to fetch
     * @param password: password to check
     * @return User object or null if not found
     */
    public User authenticateUser(Long id, String password) {
        User user = findById(id);
        if (user != null)
            if (encoder.matches(password, user.getPassword()))
                return user;
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);
        if (!user.isPresent()) throw new UsernameNotFoundException("User not found");
        return new User(user.get().getId(), user.get().getUsername(), user.get().getPassword());
    }
}
