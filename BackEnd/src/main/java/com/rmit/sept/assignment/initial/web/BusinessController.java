package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Business;
import com.rmit.sept.assignment.initial.service.BusinessService;
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
    BusinessService businessService;


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
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                return new ResponseEntity<List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            }
        }
        Business business1 = businessService.saveOrUpdateBusiness(business);
        HttpStatus status = (business1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
        return new ResponseEntity<Business>(business, status);
    }
}
