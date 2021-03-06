package com.rmit.sept.assignment.initial.repositories;


import com.rmit.sept.assignment.initial.model.Business;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends CrudRepository<Business, Long> {
    @Override
    Optional<Business> findById(Long aLong);
    List<Business> findAllByNameIgnoreCase(String name);
    List<Business> findAllByNameContainsIgnoreCase(String name);
}
