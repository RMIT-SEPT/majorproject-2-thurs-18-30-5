package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.BusinessRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.model.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Business Services is an intermediary between Business Repo and Controller
 */
@Service
public class BusinessService {
    @Autowired
    BusinessRepository businessRepository;
    @Autowired
    WorkerRepository workerRepository;

    /**
     * Service method to update or create a Business
     * @param business Business object to create or update
     * @return Created/updated Business object, or null if invalid (no business id or name)
     */
    public Business saveOrUpdateBusiness(Business business) {
        String businessName = business.getName();
        if (businessName != null) {
            return businessRepository.save(business);
        } else
            return null;
    }

    /**
     * Returns a list of all Businesses
     * @return a Collection of businesses (ArrayList)
     */
    public Collection<Business> findAll() {
        List<Business> businesses = new ArrayList<>();
        businessRepository.findAll().forEach(businesses::add);
        return businesses;
    }

    /**
     * Return a business based on their ID
     * @param id id of Business to fetch
     * @return Business object, or null if not found
     */
    public Business findById(Long id) {
        if (id == null) return null;
        return businessRepository.findById(id).orElse(null);
    }

    /**
     * Return a list of business containing a substring (case-insensitive)
     * @param name Name of businesses to search for (substring)
     * @return Collection of businesses
     */
    public Collection<Business> findByName(String name) {
        if (name == null) return null;
        return businessRepository.findAllByNameContainsIgnoreCase(name);
    }


    /**
     * Overloaded findByName to search for exact name match (case-insensitive)
     * @param name Name of business to match
     * @param exact true if exact match
     * @return List of businesses
     */
    public Collection<Business> findByName(String name, boolean exact) {
        if (name == null) return null;
        if (exact)
            return businessRepository.findAllByNameIgnoreCase(name);
        else
            return findByName(name);
    }
}
