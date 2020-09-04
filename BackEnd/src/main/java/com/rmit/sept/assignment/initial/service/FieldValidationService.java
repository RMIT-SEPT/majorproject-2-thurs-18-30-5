package com.rmit.sept.assignment.initial.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Amir Homayoon Ashrafzadeh
 * Source: https://github.com/RMIT-SEPT/spring-boot-demo
 */
@Service
public class FieldValidationService {

    public ResponseEntity<?> mapFieldErrors(BindingResult result) {

        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        return null;

    }
}