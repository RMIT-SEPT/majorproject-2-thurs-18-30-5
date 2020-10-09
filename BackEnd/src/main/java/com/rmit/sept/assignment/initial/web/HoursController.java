package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import com.rmit.sept.assignment.initial.service.HoursService;
import com.rmit.sept.assignment.initial.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.rmit.sept.assignment.initial.security.SecurityConstant.*;
/**
 * Hours Controller class allows access to update/retrieve/remove Hours entities
 * Hours are use to indicate when a Worker is working, taking a composite primary key value of WorkerId (FK) and DayOfWeek
 */
@CrossOrigin
@RestController
@RequestMapping("/api/hours")
public class HoursController {
    @Autowired
    private HoursService hoursService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private AuthRequestService authService;

    @Autowired
    private FieldValidationService validationService;

    /**
     * Endpoint to return Hours for a worker
     * @param workerId id of worker
     * @return Collection of Hours for that worker
     */
    @GetMapping(value = "/{workerId}")
    public ResponseEntity<Collection<Hours>> getWorkerHours(@PathVariable Long workerId) {
        Worker worker = workerService.findById(workerId);
        if (worker != null) {
            Collection<Hours> workerHours = hoursService.findByWorker(worker);
            return new ResponseEntity<>(workerHours, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to return an Hours entity based on id
     * @param workerId id of worker
     * @param dayOfWeek enum value for DayOfWeek
     * @return Hours entity if found, otherwise null
     */
    @GetMapping(value = "/{workerId}", params = "dayOfWeek")
    public ResponseEntity<?> getWorkerHoursByDay(@PathVariable Long workerId, @RequestParam DayOfWeek dayOfWeek) {
        Worker worker = workerService.findById(workerId);
        if (worker != null) {
            Hours hours = hoursService.findById(worker, dayOfWeek);
            HttpStatus status = (hours == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<Hours>(hours, status);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Endpoint to return a list of available hours for a worker - available hours are time-slots on a given date where
     * the Worker has no booking, and is scheduled to be working
     * @param workerId: id of Worker to fetch
     * @param date: date to check
     * @return a List of Hours, which represent the workers' available time on that date
     */
    @GetMapping(value = "/available/{workerId}", params = "date")
    public ResponseEntity<?> getWorkerAvailableHours(@PathVariable Long workerId,
                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Worker worker = workerService.findById(workerId);
        System.err.println(date);
        if (worker != null && date != null) {
            List<Hours> hoursList = hoursService.findAvailableByWorker(worker, date);
            HttpStatus status = (hoursList == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<List<Hours>>(hoursList, status);
        }
        return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
    }

    /**
     * Endpoint to create hours for a worker
     * @param workerId id of worker
     * @param hours Hours object to update
     * @param result field errors/validation based on Hours entity
     * @return newly created Hours entity if successful
     */
    @PostMapping(value = "/{workerId}")
    public ResponseEntity<?> createNewHours(@RequestHeader(value = HEADER_NAME, required = false) String token,
                                            @PathVariable Long workerId,
                                            @Validated @RequestBody Hours hours,
                                            BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null && workerId != null) {
            Hours hours1 = null;
            Worker w1 = workerService.findById(workerId);
            if (authService.authWorkerRequest(token, w1)) {
                if (w1 != null) {
                    Hours.HoursPK pk = hours.getId();
                    pk.setWorker(w1);
                    hours.setId(pk);
                    hours1 = hoursService.saveOrUpdateHours(hours);
                }
                HttpStatus status = (hours1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                return new ResponseEntity<>(hours1, status);
            }
            return new ResponseEntity<>("Unauthorised to perform request", HttpStatus.UNAUTHORIZED);
        } else {
            return errors;
        }
    }

    /**
     * Endpoint to delete hours for a worker
     * @param workerId id of Worker
     * @param dayOfWeek day of week, enum type DayOfWeek
     * @return OK if the id values were valid, otherwise BAD_REQUEST
     */
    @DeleteMapping(value = "/{workerId}", params = "dayOfWeek")
    public ResponseEntity<?> deleteWorkerHours(@RequestHeader(value = HEADER_NAME, required = false) String token,
                                               @PathVariable Long workerId,
                                               @RequestParam DayOfWeek dayOfWeek) {
        boolean deleteHours = false;
        if (workerId != null && dayOfWeek != null) {
            if (authService.authWorkerRequest(token, workerId)) {
                deleteHours = hoursService.deleteHours(workerId, dayOfWeek);
            }
        }
        HttpStatus status = (deleteHours) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>("Hours deleted", status);
    }
}
