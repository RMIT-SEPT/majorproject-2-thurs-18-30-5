package com.rmit.sept.assignment.initial.Repositories;

import com.rmit.sept.assignment.initial.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Override
    Iterable<Customer> findAllById(Iterable<Long> iterable);
}
