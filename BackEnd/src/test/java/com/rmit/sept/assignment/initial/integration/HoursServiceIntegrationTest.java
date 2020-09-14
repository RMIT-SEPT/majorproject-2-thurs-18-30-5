package com.rmit.sept.assignment.initial.integration;

import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.service.HoursService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HoursServiceIntegrationTest {
    @Autowired
    private HoursService service;
}