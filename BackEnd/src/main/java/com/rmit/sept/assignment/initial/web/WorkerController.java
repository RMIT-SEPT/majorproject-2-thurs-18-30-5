package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import com.rmit.sept.assignment.initial.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @Autowired
    private FieldValidationService validationService;

    /**
     * Endpoint to return all workers
     * @return a Collection of Worker objects
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<Worker>> getWorkers() {
        return new ResponseEntity<>(workerService.findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to return a Worker based on id
     * @param id Long id of Worker to fetch
     * @return Worker object and HttpStatus.OK if found, otw null and HttpStatus.NOT_FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Long id) {
        Worker worker = workerService.findById(id);
        // if the service return null this means no worker was found
        HttpStatus status = worker != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(worker, status);
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<Worker>> getWorkersByBusiness(
            @PathVariable Long businessId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end) {
        List<Worker> workers;
        if (start != null && end != null) {
            System.out.println("START AND END");
            workers = workerService.findAllByBusiness(businessId, start, end);
        } else {
            workers = workerService.findAllByBusiness(businessId);
        }
        HttpStatus status = (workers.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(workers, status);
    }

    /**
     * Endpoint to create a new Worker
     * @param worker Worker object to create
     * @param result field errors/validation based on Worker entity restrictions
     * @return Worker object and corresponding status code (CREATED or BAD_REQUEST)
     */
    @PostMapping("")
    public ResponseEntity<?> createNewWorker(@Validated @RequestBody Worker worker, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            Worker worker1 = workerService.saveOrUpdateWorker(worker);
            HttpStatus status = (worker1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
            return new ResponseEntity<Worker>(worker1, status);
        } else {
            return errors;
        }
    }

    /**
     * Endpoint to update an existing worker
     * @param worker: Worker object with updated details, and current password (plaintext)
     * @param result: field errors/validation based on Worker entity
     * @return ResponseEntity with updated Worker object and corresponding status code (CREATED, BAD_REQUEST)
     */
    @PutMapping("")
    public ResponseEntity<?> updateWorker(@Validated @RequestBody Worker worker, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            if (getWorker(worker.getId()) != null) {
                // TODO: check that this works - or do you need to set id from path
                Worker worker1 = workerService.saveOrUpdateWorker(worker);
                HttpStatus status = (worker1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                return new ResponseEntity<Worker>(worker1, status);
            }
            return new ResponseEntity<>("Invalid Worker ID", HttpStatus.NOT_FOUND);
        } else {
            return errors;
        }
    }


    @GetMapping("/test/{workerId}")
    public ResponseEntity<?> getTestHours(
            @PathVariable Long workerId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = "2020-16-09 14:00";
        String e = "2020-16-09 15:00";
//        Date start = df.parse(s);
//        Date end = df.parse(e);
        return new ResponseEntity<>(workerService.checkAvailability(workerId, start, end), HttpStatus.OK);
    }
}