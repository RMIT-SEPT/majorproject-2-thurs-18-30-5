package com.rmit.sept.assignment.initial.repositories;

import com.rmit.sept.assignment.initial.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    Iterable<User> findAllById(Iterable<Long> iterable);

    Optional<User> findByUsername(String username);

}
