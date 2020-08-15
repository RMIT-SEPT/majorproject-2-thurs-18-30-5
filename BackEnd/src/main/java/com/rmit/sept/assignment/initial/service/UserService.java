package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.Repositories.UserRepository;
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

    public User saveOrUpdateUser(User user) {
        Long userId = user.getId();
        if (userId == null) {
            String username = user.getUsername();
            if (username != null) {
                Optional<User> user1 = userRepository.findByUsername(username);
                user1.ifPresent(value -> user.setId(value.getId()));
            } else {
                return null;
            }
        }
        return userRepository.save(user);
    }

    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User findById(Long id) {
        Optional<User> customer = userRepository.findById(id);
        return customer.orElse(null);
    }

}
