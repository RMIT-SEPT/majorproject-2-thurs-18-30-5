package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Worker Service acts as an intermediary between the Repo and Controller classes, providing additional validation and
 * handling of requests.
 */
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

    /**
     * Returns all Workers based on their assigned Business.
     * @param bid id of Business
     * @return Collection of Workers assigned to that Business
     */
    public List<Worker> findAllByBusiness(Long bid) {
        return new ArrayList<>(workerRepository.findAllByBusiness_Id(bid));
    }

    /**
     * Returns all Workers based on their assigned Business. Also filters based on LocalDateTime start and end values,
     * returning only Workers who have no existing bookings between those dates
     * @param bid id of Business
     * @param startDate start date-time value
     * @param endDate end date-time value
     * @return List of available Workers assigned to that Business
     */
    public List<Worker> findAllByBusiness(Long bid, LocalDateTime startDate, LocalDateTime endDate) {
        List<Worker> workers = new ArrayList<>();
        for (Worker worker : workerRepository.findAllByBusiness_Id(bid)) {
            if (checkAvailability(worker.getId(), startDate, endDate)) {
                System.err.println("ADDING " + worker.getId());
                workers.add(worker);
            }
        }
        return workers;
    }

    /**
     * Checks if a worker is available between two LocalDateTime values, by making use of the Utilities.findOverlap function.
     * Also provides minor validation of fields before checking for an overlap with the new start/end dates
     * @param workerId id of worker to check
     * @param startDate start Date-time value
     * @param endDate end Date-time value
     * @return true if the worker is available, otherwise false
     */
    public boolean checkAvailability(Long workerId, LocalDateTime startDate, LocalDateTime endDate) {
        Date start = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
        for (Hours hours : hoursRepository.findById_WorkerId(workerId)) {
            if (hours.getId().getDayOfWeek().compareTo(startDate.getDayOfWeek()) == 0) {
                if ((startDate.toLocalTime().compareTo(hours.getStart()) >= 0) &&
                        (endDate.toLocalTime().compareTo(hours.getEnd()) <= 0)) {
                    Booking temp = new Booking();
                    temp.setStart(start);
                    temp.setEnd(end);
                    List<Booking> bookings = findById(workerId).getBookings().stream()
                            .filter(b -> b.getStatus() == Booking.BookingStatus.PENDING).collect(Collectors.toList());
                    bookings.add(temp);  // add proposed booking dates to check for an overlap with existing bookings
                    return !Utilities.findOverlap(bookings);
                }
            }
        }
        return false;
    }
}
