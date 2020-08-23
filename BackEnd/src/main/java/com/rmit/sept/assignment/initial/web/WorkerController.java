package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

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

    /**
     * Endpoint to create a new Worker
     * @param worker Worker object to create
     * @param result field errors/validation based on Worker entity restrictions
     * @return Worker object and corresponding status code (CREATED or BAD_REQUEST)
     */
    @PostMapping("")
    public ResponseEntity<?> createNewWorker(@Validated @RequestBody Worker worker, BindingResult result) {
        if (result.hasErrors()) {  // validate that fields are correct format/type
            for (FieldError error : result.getFieldErrors()) {
                return new ResponseEntity<List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            }
        }
        Worker worker1 = workerService.saveOrUpdateWorker(worker);
        HttpStatus status = (worker1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
        return new ResponseEntity<Worker>(worker1, status);
    }
}