package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder encoder;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HoursRepository hoursRepository;

    public WorkerService(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }


    /**
     * Service method to update or create a worker.
     * This requires a valid user to exist (worker.id references user.id)
     * @param worker Worker object to create or update
     * @return Worker object, or null if invalid (no id)
     */
    public Worker saveOrUpdateWorker(Worker worker) {
        if (worker == null) return null;
        Long workerId = worker.getId();
        if (workerId != null) {  // workerId cannot be null
            Optional<Worker> worker1 = workerRepository.findById(workerId);
            if (worker1.isPresent()) {  // UPDATE WORKER i.e. worker with that ID already exists
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
     * Authenticate a worker based on username and password. Also authenticates admin users
     * @param id id of worker
     * @param password password attept
     * @param admin admin flag - true if auth for an admin user
     * @return Worker object if password and admin are valid otherwise null
     */
    public Worker authenticateWorker(Long id, String password, boolean admin) {
        if (id == null || password == null) return null;
        Worker worker;
        worker = findByIdAndAdmin(id, admin);
        if (worker != null && worker.getUser() != null) {
            if (encoder.matches(password, worker.getUser().getPassword())) {
                return worker;
            }
        }
        return null;
    }

    /**
     * Overloaded authentication service to allow for filtering by username instead.
     * Calls the above method authenticateWorker(Long id, String password, boolean admin) to carry out authentication
     * @param username username of Worker (user object field)
     * @param password password of Worker
     * @param admin admin flag - true if auth for an admin
     * @return Worker object or null if invalid
     */
    public Worker authenticateWorker(String username, String password, boolean admin) {
        if (username == null || password == null) return null;
        Worker worker = findByUsername(username);
        if (worker == null) return null;
        return authenticateWorker(worker.getId(), password, admin);
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
        if (id == null) return null;
        return workerRepository.findById(id).orElse(null);
    }

    /**
     * Return a worker based on id value and admin boolean status
     * @param id id of worker
     * @param admin boolean admin status
     * @return Worker if found or null
     */
    public Worker findByIdAndAdmin(Long id, boolean admin) {
        if (id == null) return null;
        return workerRepository.findByIdAndIsAdmin(id, admin).orElse(null);
    }

    /**
     * Returns a worker based on their username (User object)
     * @param username username of the worker
     * @return Worker object or null if not found
     */
    public Worker findByUsername(String username) {
        if (username == null) return null;
        return workerRepository.findByUserUsername(username).orElse(null);
    }

    /**
     * Returns all Workers based on their assigned Business.
     * @param bid id of Business
     * @return Collection of Workers assigned to that Business
     */
    public List<Worker> findAllByBusiness(Long bid, Boolean isAdmin) {
        if (bid == null) return null;
        if (isAdmin != null) {
            return new ArrayList<>(workerRepository.findAllByBusiness_IdAndIsAdmin(bid, isAdmin));
        }
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
    public List<Worker> findAllByBusiness(Long bid, LocalDateTime startDate, LocalDateTime endDate, Boolean isAdmin) {
        if (bid == null || startDate == null || endDate == null || isAdmin == null) return null;
        List<Worker> workers = new ArrayList<>();
        for (Worker worker : findAllByBusiness(bid, isAdmin)) {
            if (checkAvailability(worker.getId(), startDate, endDate)) {
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
    private boolean checkAvailability(Long workerId, LocalDateTime startDate, LocalDateTime endDate) {
        for (Hours hours : hoursRepository.findById_WorkerId(workerId)) {
            if (hours.getId().getDayOfWeek().compareTo(startDate.getDayOfWeek()) == 0) {
                if ((startDate.toLocalTime().compareTo(hours.getStart()) >= 0) &&
                        (endDate.toLocalTime().compareTo(hours.getEnd()) <= 0)) {
                    Booking temp = new Booking();
                    temp.setStart(startDate);
                    temp.setEnd(endDate);
                    List<Booking> bookings = findById(workerId).getBookings().stream()
                            .filter(b -> b.getStatus() == Booking.BookingStatus.PENDING || b.getStatus() == Booking.BookingStatus.CONFIRMED).collect(Collectors.toList());
                    bookings.add(temp);  // add proposed booking dates to check for an overlap with existing bookings
                    return !Utilities.findOverlap(bookings);
                }
            }
        }
        return false;
    }
}
