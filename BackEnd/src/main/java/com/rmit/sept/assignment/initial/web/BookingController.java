package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.service.BookingService;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private FieldValidationService validationService;

    private final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("/all")
    public ResponseEntity<Collection<Booking>> getBookings() {
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        HttpStatus status = booking != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(booking, status);
    }

    @GetMapping("/all/worker/{id}")
    public ResponseEntity<Collection<Booking>> getBookingsByWorker(@PathVariable Long id) {
        Collection<Booking> bookings = bookingService.findByWorker(id);
        HttpStatus status = (bookings.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(bookings, status);
    }

    @GetMapping("/all/business/{id}")
    public ResponseEntity<Collection<Booking>> getBookingsByBusiness(@PathVariable Long id) {
        Collection<Booking> bookings = bookingService.findByBusiness(id);
        HttpStatus status = (bookings.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(bookings, status);
    }

    @GetMapping("/all/user/{id}")
    public ResponseEntity<Collection<Booking>> getBookingsByUser(@PathVariable Long id,
                                                                 @RequestParam Booking.BookingStatus bookingStatus) {
        Collection<Booking> bookings;
        if (bookingStatus == null)
            bookings = bookingService.findByUser(id);
        else
            bookings = bookingService.findByUser(id, bookingStatus);
        HttpStatus status = (bookings.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(bookings, status);
    }

    @PostMapping("")
    public ResponseEntity<?> createBooking(@Validated @RequestBody Booking booking, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            Booking booking1 = bookingService.saveOrUpdateBooking(booking, true);
            HttpStatus status = (booking1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
            return new ResponseEntity<Booking>(booking1, status);
        } else {
            return errors;
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@Validated @RequestBody Booking booking, @PathVariable Long id, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            if (bookingService.findById(id) != null) {
                booking.setId(id);  // TODO: why is this required? postman sending id as field is ignored somehow
                Booking booking1 = bookingService.saveOrUpdateBooking(booking, false);
                HttpStatus status = (booking1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
                return new ResponseEntity<Booking>(booking1, status);
            }
            return new ResponseEntity<>("Invalid Booking ID", HttpStatus.NOT_FOUND);
        } else {
            return errors;
        }
    }
}
