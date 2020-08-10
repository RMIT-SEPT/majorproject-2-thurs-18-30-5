package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.Repositories.CustomerRepository;
import com.rmit.sept.assignment.initial.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveOrUpdateCustomer(Customer customer) {
//        logic
        return customerRepository.save(customer);
    }

}
