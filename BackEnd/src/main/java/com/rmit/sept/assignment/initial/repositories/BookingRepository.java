package com.rmit.sept.assignment.initial.repositories;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Override
    Optional<Booking> findById(Long aLong);
    @Override
    Iterable<Booking> findAllById(Iterable<Long> iterable);
    List<Booking> findAllByUser(User user);
    List<Booking> findAllByUser_Id(Long userId);
    List<Booking> findAllByUser_IdAndStatus(Long userId, Booking.BookingStatus status);
    List<Booking> findAllByWorker(Worker worker);
    List<Booking> findAllByWorker_Id(Long workerId);
    List<Booking> findAllByWorker_IdAndStatus(Long workerId, Booking.BookingStatus status);
    List<Booking> findAllByWorker_Business_Id(Long businessId);
    List<Booking> findAllByWorker_Business_IdAndStatus(Long businessId, Booking.BookingStatus status);
    List<Booking> findAllByStatus(Booking.BookingStatus status);
    List<Booking> findAllByWorker_IdAndStatusOrWorker_IdAndStatus(@NotNull(message = "Worker ID must be provided") Long worker_id, Booking.BookingStatus status, @NotNull(message = "Worker ID must be provided") Long worker_id2, Booking.BookingStatus status2);
    List<Booking> findAllByUser_IdAndStatusOrUser_IdAndStatus(@NotNull(message = "User ID must be provided") Long user_id, Booking.BookingStatus status, @NotNull(message = "User ID must be provided") Long user_id2, Booking.BookingStatus status2);
}
