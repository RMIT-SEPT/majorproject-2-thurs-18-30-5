package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.BookingService;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

import static com.rmit.sept.assignment.initial.security.SecurityConstant.*;

/**
 * Booking Controller class allows access to update retrieve and create Booking objects. Bookings are made between User
 * entities and Workers.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private AuthRequestService authService;

    @Autowired
    private FieldValidationService validationService;

    private final Logger logger = LoggerFactory.getLogger(BookingController.class);

    /**
     * Endpoint to return all Bookings
     * @return a Collection of Booking entities
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<Booking>> getBookings(@RequestParam(required = false) Booking.BookingStatus bookingStatus) {
        if (bookingStatus == null)
            return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
        else
            return new ResponseEntity<>(bookingService.findAll(bookingStatus), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a Booking based on ID value
     * @param id id of Booking
     * @return Booking entity if found otherwise null
     */
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        HttpStatus status = booking != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(booking, status);
    }

    /**
     * Endpoint to retrieve a Booking based on the Worker assigned to it
     * @param id id of Worker entity
     * @return Collection of Bookings made for that Worker
     */
    @GetMapping("/all/worker/{id}")
    public ResponseEntity<Collection<Booking>> getBookingsByWorker(@PathVariable Long id,
                                                                   @RequestParam(required = false) Booking.BookingStatus bookingStatus) {
        Collection<Booking> bookings;
        if (bookingStatus == null)
            bookings = bookingService.findByWorker(id);
        else
            bookings = bookingService.findByWorker(id, bookingStatus);
        HttpStatus status = (bookings.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(bookings, status);
    }

    /**
     * Endpoint to return Bookings that have been made with any worker of a Business
     * @param id id of Business to match
     * @return Collection of Bookings for the Business
     */
    @GetMapping("/all/business/{id}")
    public ResponseEntity<Collection<Booking>> getBookingsByBusiness(@PathVariable Long id,
                                                                     @RequestParam(required = false) Booking.BookingStatus bookingStatus) {
        Collection<Booking> bookings;
        if (bookingStatus == null)
            bookings = bookingService.findByBusiness(id);
        else
            bookings = bookingService.findByBusiness(id, bookingStatus);
        HttpStatus status = (bookings.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(bookings, status);
    }

    /**
     * Endpoint to return Bookings that have been made by a User/Customer
     * @param id id of User/Customer
     * @param bookingStatus optional parameter, to allow for filtering by BookingStatus (PENDING, COMPLETED, CANCELLED)
     * @return Collection of Booking entities
     */
    @GetMapping("/all/user/{id}")
    public ResponseEntity<Collection<Booking>> getBookingsByUser(@PathVariable Long id,
                                                                 @RequestParam(required = false) Booking.BookingStatus bookingStatus) {
        Collection<Booking> bookings;
        if (bookingStatus == null)
            bookings = bookingService.findByUser(id);
        else
            bookings = bookingService.findByUser(id, bookingStatus);
        HttpStatus status = (bookings.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(bookings, status);
    }

    /**
     * Endpoint to create a new Booking entity
     * @param booking Booking entity to create
     * @param result field validation/errors based on Booking entity
     * @return newly created Booking if successful, otherwise null
     */
    @PostMapping("")
    public ResponseEntity<?> createBooking(@RequestHeader(value = HEADER_NAME, required = false) String token,
                                           @Validated @RequestBody Booking booking,
                                           BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            if (authService.authBookingRequest(token, booking.getUser(), booking.getWorker())) {
                Booking booking1 = bookingService.saveOrUpdateBooking(booking, true);
                HttpStatus status = (booking1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                return new ResponseEntity<Booking>(booking1, status);
            }
            return new ResponseEntity<String>("Unauthorised to perform request", HttpStatus.UNAUTHORIZED);
        }
        return errors;
    }

    /**
     * Endpoint to update a Booking record
     * @param booking Booking entity to update
     * @param id id of Booking, used to verify existence
     * @param result field errors/validation based on Booking entity
     * @return Booking created if the process was successful, or null otherwise
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@RequestHeader(value = HEADER_NAME, required = false) String token,
                                           @Validated @RequestBody Booking booking,
                                           @PathVariable Long id, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            Booking temp = bookingService.findById(id);
            if (temp != null) {
                if (authService.authBookingRequest(token, temp.getUser(), temp.getWorker())) {
                    booking.setId(id);
                    Booking booking1 = bookingService.saveOrUpdateBooking(booking, false);
                    HttpStatus status = (booking1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
                    return new ResponseEntity<Booking>(booking1, status);
                }
                return new ResponseEntity<String>("Unauthorised to perform request", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<String>("Invalid Booking ID", HttpStatus.NOT_FOUND);
        }
        return errors;
    }
}
