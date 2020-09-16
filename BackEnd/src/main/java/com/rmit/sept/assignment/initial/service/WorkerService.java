package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.repositories.BookingRepository;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HoursRepository hoursRepository;


    /**
     * Service method to update or create a worker.
     * This requires a valid user to exist (worker.id references user.id)
     * @param worker Worker object to create or update
     * @return Worker object, or null if invalid (no id)
     */
    public Worker saveOrUpdateWorker(Worker worker) {
        Long workerId = worker.getId();
        if (workerId != null) {  // workerId cannot be null
            Optional<Worker> worker1 = workerRepository.findById(workerId);
            if (worker1.isPresent()) {  // UPDATE WORKER
                worker.setUser(worker1.get().getUser());
                return workerRepository.save(worker);
            }
            Optional<User> user = userRepository.findById(workerId);
            if (user.isPresent()) {  // CREATE WORKER i.e. user has been created but worker has not
                worker.setUser(user.get());
                return workerRepository.save(worker);
            }
        }
        return null;
    }

    /**
     * Returns all workers
     * @return an ArrayList of Worker objects
     */
    public Collection<Worker> findAll() {
        List<Worker> workers = new ArrayList<>();
        workerRepository.findAll().forEach(workers::add);
        return workers;
    }

    /**
     * Returns a Worker object based on id value
     * @param id id of Worker to fetch
     * @return Worker object if found, otherwise null
     */
    public Worker findById(Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        return worker.orElse(null);
    }

    public List<Worker> findAllByBusiness(Long bid) {
        return new ArrayList<>(workerRepository.findAllByBusiness_Id(bid));
    }

    public List<Worker> findAllByBusiness(Long bid, LocalDateTime startDate, LocalDateTime endDate) {
        List<Worker> workers = new ArrayList<>();
        for (Worker worker : workerRepository.findAllByBusiness_Id(bid)) {
            if (checkAvailability(worker.getId(), startDate, endDate)) {
                workers.add(worker);
            }
        }
        return workers;
    }

    public boolean checkAvailability(Long workerId, LocalDateTime startDate, LocalDateTime endDate) {
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
        Date start = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
        for (Hours hours : hoursRepository.findById_WorkerId(workerId)) {
            if (hours.getId().getDayOfWeek().compareTo(startDate.getDayOfWeek()) == 0) {
                if ((tf.format(start).compareTo(tf.format(hours.getStart())) >= 0) &&
                        (tf.format(end).compareTo(tf.format(hours.getEnd())) <= 0)) {
                    Booking temp = new Booking();
                    temp.setStart(start);
                    temp.setEnd(end);
                    List<Booking> bookings = findById(workerId).getBookings();
                    bookings.add(temp);
                    return !Utilities.findOverlap(bookings);
                }
            }
        }
        return false;
    }
}
