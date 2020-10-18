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
     * Service method to update or create a user - user token (password when calling update endpoint) is authenticated
     * in UserController before calling this method.
     * @param user user data to update
     * @return User object, or null if not found (i.e. when updating via username)
     */
    public User saveOrUpdateUser(User user, boolean create) {
        if (user == null) return null;
        if (create) {  // creating a user
            String username = user.getUsername();
            if (username != null) {  // must provide a username value when creating a user
                Optional<User> user1 = userRepository.findByUsername(username);
                if (user1.isPresent()) return null;  // return null if duplicate username and creating a user
            } else {  // if we cannot find by username then the request was incorrect
                return null;
            }
        } else {  // update user
            Long userId = user.getId();
            if (userId != null) {  // if ID is null we should check for username
                String username = user.getUsername();
                if (username != null) {
                    Optional<User> user1 = userRepository.findByUsername(username);
                    // return null if duplicate username and updating a user
                    if (user1.isPresent() && !userId.equals(user1.get().getId())) return null;
                    user1.ifPresent(value -> user.setId(value.getId()));
                } else {  // if we cannot find by username or user id then the request was invalid
                    return null;
                }
            } else {
                return null;
            }
        }
        String password = user.getPassword();
        if (password != null) {
            user.setPassword(encoder.encode(user.getPassword()));
        } else {
            return null;
        }
        return userRepository.save(user);
    }

    /**
     * Returns a list of all Users
     * @return a Collection of workers (ArrayList)
     */
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Return a user based on their ID value (primary key)
     * @param id Long id of user (generated value)
     * @return User object, or null if not found
     */
    public User findById(Long id) {
        if (id == null) return null;
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Return a user based on username (unique field)
     * @param username: unique username of user
     * @return User object of null if not found
     */
    public User findByUsername(String username) {
        if (username == null) return null;
        return userRepository.findByUsername(username).orElse(null);
    }

    /**
     * Authenticate a user - check username and password are valid
     * @param username: username of user to fetch
     * @param password: password to check
     * @return User object or null invalid credentials
     */
    public User authenticateUser(String username, String password) {
        if (username == null || password == null) return null;
        User user = findByUsername(username);
        if (user != null)
            if (encoder.matches(password, user.getPassword()))  // compare encrypted and plaintext password
                return user;
        return null;
    }

    /**
     * Authenticate a user - check ID and password
     * @param id: id of user to fetch
     * @param password: password to check
     * @return User object or null invalid credentials
     */
    public User authenticateUser(Long id, String password) {
        if (id == null || password == null) return null;
        User user = findById(id);
        if (user != null)
            if (encoder.matches(password, user.getPassword()))  // compare encrypted and plaintext password
                return user;
        return null;
    }

    /**
     * Overrides UserDetailsService method - loads a user by username, used in token authentication
     * @param username: username to load
     * @return User object with ID, username, and password
     * @throws UsernameNotFoundException: occurs when username not found in DB
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) return null;
        User user = findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new User(user.getId(), user.getUsername(), user.getPassword());
    }
}
