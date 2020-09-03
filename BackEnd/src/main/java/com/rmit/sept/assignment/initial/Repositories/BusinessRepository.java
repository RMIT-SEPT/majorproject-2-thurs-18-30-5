package com.rmit.sept.assignment.initial.Repositories;


import com.rmit.sept.assignment.initial.model.Business;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends CrudRepository<Business, Long> {
    @Override
    Optional<Business> findById(Long aLong);
    List<Business> findAllByName(String name);
    List<Business> findAllByNameContains(String name);
}
