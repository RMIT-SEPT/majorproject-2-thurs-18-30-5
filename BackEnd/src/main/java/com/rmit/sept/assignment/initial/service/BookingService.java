package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.repositories.BookingRepository;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

import static com.rmit.sept.assignment.initial.service.Utilities.findOverlap;

/**
 * Booking Service acts as an intermediary between the Booking Repository and Controller
 */
@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(BookingService.class);

    /**
     * Service method to update or create a booking.
     * A create/update is invalid if any of the following are true:
     *  - null in (user_id, booking_id, worker_id)
     *  - user is a worker
     *  - start is after end date
     * @param booking Booking object to create/update
     * @return updated Booking object, or null if invalid
     */
    public Booking saveOrUpdateBooking(@NotNull Booking booking, boolean create) {
        Long workerId = booking.getWorker().getId();
        Long userId = booking.getUser().getId();
        if (workerId == null || userId == null) {
            return null;  // booking must have these values
        }
        Optional<Worker> worker1 = workerRepository.findById(workerId);
        Optional<User> user1 = userRepository.findById(userId);
        Optional<Worker> userIsWorker = workerRepository.findById(userId);
        if (!worker1.isPresent() || !user1.isPresent() || userIsWorker.isPresent()) {
            return null; // check if user/worker are invalid, or if the user is also a worker (not allowed - invalid)
        }
        List<Booking> userBookings = new ArrayList<>(
                this.findByUser(userId, Booking.BookingStatus.PENDING, Booking.BookingStatus.CONFIRMED));
        List<Booking> workerBookings = new ArrayList<>(
                this.findByWorker(workerId, Booking.BookingStatus.PENDING, Booking.BookingStatus.CONFIRMED));
        Date start = booking.getStart();
        Date end = booking.getEnd();
        if (start == null || end == null || !end.after(start)) {
            return null;  // validate that the end and start values are logically correct
        } else {
            if (create) {  // append new booking and check for an overlap
                userBookings.add(booking);
                workerBookings.add(booking);
            }
            if (findOverlap(userBookings) || findOverlap(workerBookings)) return null;  // found an overlap with user or worker
        }
        booking.setUser(user1.get());
        booking.setWorker(worker1.get());
        return bookingRepository.save(booking);
    }


    /**
     * Method to return all bookings
     * @return Collection of Booking objects
     */
    public Collection<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findAll().forEach(bookings::add);
        return bookings;
    }

    /**
     * Return a Booking based on booking id
     * @param bookingId Booking id value
     * @return Booking object if found, otherwise null
     */
    public Booking findById(@NotNull Long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    /**
     * Return Bookings based on WorkerID
     * @param workerId id of Worker
     * @return Collection of Bookings made for that Worker
     */
    public Collection<Booking> findByWorker(@NotNull Long workerId) {
        return bookingRepository.findAllByWorker_Id(workerId);
    }

    /**
     * Return Bookings based on Worker id and Booking status
     * @param workerId id of Worker
     * @param status BookingStatus of Booking (PENDING, CONFIRMED, COMPLETED, CANCELLED)
     * @return Collection of Bookings
     */
    public Collection<Booking> findByWorker(@NotNull Long workerId, Booking.BookingStatus status) {
        return bookingRepository.findAllByWorker_IdAndStatus(workerId, status);
    }

    /**
     * Return Bookings based on Worker id and two Booking statuses
     * @param workerId id of Worker
     * @param status1 BookingStatus of Booking (PENDING, CONFIRMED, COMPLETED, CANCELLED)
     * @param status2 BookingStatus of Booking (PENDING, CONFIRMED, COMPLETED, CANCELLED)
     * @return Collection of Bookings
     */
    public Collection<Booking> findByWorker(@NotNull Long workerId, Booking.BookingStatus status1, Booking.BookingStatus status2) {
        return bookingRepository.findAllByWorker_IdAndStatusOrStatus(workerId, status1, status2);
    }

    /**
     * Return Bookings based on User who booked them
     * @param userId id of User
     * @return Collection of Bookings for that User
     */
    public Collection<Booking> findByUser(@NotNull Long userId) {
        return bookingRepository.findAllByUser_Id(userId);
    }

    /**
     * Return Bookings based on User and BookingStatus
     * @param userId id of User
     * @param status BookingsStatus value (PENDING, CONFIRMED, COMPLETED, CANCELLED)
     * @return Collection of Booking entities
     */
    public Collection<Booking> findByUser(@NotNull Long userId, @NotNull Booking.BookingStatus status) {
        return bookingRepository.findAllByUser_IdAndStatus(userId, status);
    }

    /**
     * Return Bookings based on User and two BookingStatuses
     * @param userId id of User
     * @param status1 BookingStatus value (PENDING, CONFIRMED, COMPLETED, CANCELLED)
     * @param status2 BookingStatus value (PENDING, CONFIRMED, COMPLETED, CANCELLED)
     * @return Collection of Booking entities
     */
    public Collection<Booking> findByUser(@NotNull Long userId, @NotNull Booking.BookingStatus status1, Booking.BookingStatus status2) {
        return bookingRepository.findAllByUser_IdAndStatusOrStatus(userId, status1, status2);
    }

    /**
     * Return Bookings based on the Business of the Worker they were made with
     * @param businessId id of Business
     * @return Collection of Bookings for the Business
     */
    public Collection<Booking> findByBusiness(@NotNull Long businessId) {
        return bookingRepository.findAllByWorker_Business_Id(businessId);
    }


}
