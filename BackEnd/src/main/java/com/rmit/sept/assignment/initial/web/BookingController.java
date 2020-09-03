package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @GetMapping("/all")
    public ResponseEntity<Collection<Booking>> getBookings() {
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all/worker/{id}")
    public ResponseEntity<Collection<Booking>> getBookings(@PathVariable Long id) {
        return new ResponseEntity<>(bookingService.findByWorker(id), HttpStatus.OK);
    }
}
