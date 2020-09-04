package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.repositories.BookingRepository;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * Service method to update or create a booking.
     * A create/update is invalid if any of the following are true:
     *  - null in (user_id, booking_id, worker_id)
     *  - user is a worker
     *  - start is after end date
     * @param booking Booking object to create/update
     * @return updated Booking object, or null if invalid
     */
    public Booking saveOrUpdateBooking(@NotNull Booking booking) {
        // TODO: add additional date validation
        Long bookingId = booking.getId();
        Worker worker = booking.getWorker();
        User user = booking.getUser();
        if (bookingId != null && worker != null && user != null) {
            Optional<Worker> worker1 = workerRepository.findById(worker.getId());
            Optional<User> user1 = userRepository.findById(user.getId());
            Optional<Worker> userIsWorker = workerRepository.findById(user.getId());
            if (worker1.isPresent() && user1.isPresent() && !userIsWorker.isPresent()) {
                Date start = booking.getStart();
                Date end = booking.getEnd();
                if (start != null && end != null && end.after(start))
                    return bookingRepository.save(booking);
            }
        }
        return null;
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

    public Collection<Booking> findByWorker(@NotNull Long workerId) {
        return bookingRepository.findAllByWorker_Id(workerId);
    }

    public Collection<Booking> findByUser(@NotNull Long userId) {
        return bookingRepository.findAllByUser_Id(userId);
    }

    public Collection<Booking> findByBusiness(@NotNull Long businessId) {
        return bookingRepository.findAllByWorker_Business_Id(businessId);
    }


}
