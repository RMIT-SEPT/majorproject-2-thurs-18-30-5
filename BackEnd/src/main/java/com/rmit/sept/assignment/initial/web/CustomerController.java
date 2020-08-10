package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Customer;
import com.rmit.sept.assignment.initial.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("")
    public ResponseEntity<?> createNewCustomer(@Validated @RequestBody Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                return new ResponseEntity<List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            }
        }
//        if (result.hasErrors()) {
//            Map<String, String> errorMap = new HashMap<>();
//            result.getFieldErrors().forEach((error) -> {
//                errorMap.put(error.getField(), error.getDefaultMessage());
//            });
//            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
//        }
        Customer customer1 = customerService.saveOrUpdateCustomer(customer);
        return new ResponseEntity<Customer>(customer1, HttpStatus.CREATED);
    }
}
