package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Business;
import com.rmit.sept.assignment.initial.service.BusinessService;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

/**
 * Business Controller class allows access to retrieve create and update Business Entities. Businesses can have multiple
 * Workers, which can be booked by Customers
 */
@CrossOrigin
@RestController
@RequestMapping("/api/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private FieldValidationService validationService;

    /**
     * Endpoint to retrieve all businesses
     * @return a Collection of Business entities
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<Business>> getBusinesses() {
        return new ResponseEntity<>(businessService.findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a Business by ID
     * @param id ID of business
     * @return Business entity if found, or null
     */
    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusiness(@PathVariable Long id) {
        Business business = businessService.findById(id);
        HttpStatus status = business != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(business, status);
    }

    /**
     * Endpoint to search for businesses - allows for exact and partial matching
     * @param name String name of business to find
     * @param exact indicates if the search is to be exact or partial. If false, it will look for names containing the
     *              value of name as a substring. If true, it will only look for exact matches
     * @return Collection of Business entities matching search criteria
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Collection<Business>> getBusinessByName(@PathVariable String name, @RequestParam(required = false, defaultValue = "false") boolean exact) {
        Collection<Business> businesses;
        if (exact) {
            businesses = businessService.findByName(name, true);
        } else {
            businesses = businessService.findByName(name);
        }
        HttpStatus status = businesses.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(businesses, status);
    }

    /**
     * Endpoint to create a new Business entity. Validates fields are correct using FieldValidationService
     * @param business Business entity to create
     * @param result field errors/validation based on Business entity
     * @return new Business entity if successful otherwise a corresponding FieldError
     */
    @PostMapping("")
    public ResponseEntity<?> createNewBusiness(@Validated @RequestBody Business business, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            Business business1 = businessService.saveOrUpdateBusiness(business);
            HttpStatus status = (business1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
            return new ResponseEntity<Business>(business, status);
        } else {
            return errors;
        }
    }

    /**
     * Endpoint to update a Business entity
     * @param business Business entity to update
     * @param businessId id of Business - used to verify existence
     * @param result field errors/validation based on Business entity
     * @return newly updated Business or null if errors
     */
    @PutMapping("/{businessId}")
    public ResponseEntity<?> updateBusiness(@Validated @RequestBody Business business,
                                            @PathVariable Long businessId, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            if (businessService.findById(businessId) != null) {
                business.setId(businessId);
                HttpStatus status = (businessService.saveOrUpdateBusiness(business) == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                return new ResponseEntity<Business>(business, status);
            }
            return new ResponseEntity<>("Invalid Business ID", HttpStatus.NOT_FOUND);
        } else {
            return errors;
        }
    }
}
