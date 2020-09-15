package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Business;
import com.rmit.sept.assignment.initial.service.BusinessService;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @Autowired
    private FieldValidationService validationService;

    @GetMapping("/all")
    public ResponseEntity<Collection<Business>> getBusinesses() {
        return new ResponseEntity<>(businessService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Business> getBusiness(@PathVariable Long id) {
        Business business = businessService.findById(id);
        HttpStatus status = business != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(business, status);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Collection<Business>> getBusinessByName(@PathVariable String name, @RequestParam Optional<Boolean> exact) {
        Collection<Business> businesses;
        if (exact.isPresent() && exact.get() == Boolean.TRUE) {
            businesses = businessService.findByName(name, true);
        } else {
            businesses = businessService.findByName(name);
        }
        HttpStatus status = businesses.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(businesses, status);
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBusiness(@Validated @RequestBody Business business, Long businessId, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            if (getBusiness(businessId) != null) {
                // TODO: check that this works - or do you need to set id from path
                Business business1 = businessService.saveOrUpdateBusiness(business);
                HttpStatus status = (business1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                return new ResponseEntity<Business>(business, status);
            }
            return new ResponseEntity<>("Invalid Business ID", HttpStatus.NOT_FOUND);
        } else {
            return errors;
        }
    }
}
