package com.rmit.sept.assignment.initial.repositories;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Override
    Optional<Booking> findById(Long aLong);
    @Override
    Iterable<Booking> findAllById(Iterable<Long> iterable);
    List<Booking> findAllByUser(User user);
    List<Booking> findAllByUser_Id(Long user_id);
    List<Booking> findAllByWorker(Worker worker);
    List<Booking> findAllByWorker_id(Long worker_id);
    List<Booking> findAllByStartAfter(Date date);
    List<Booking> findAllByStatus(Booking.BookingStatus status);
}
