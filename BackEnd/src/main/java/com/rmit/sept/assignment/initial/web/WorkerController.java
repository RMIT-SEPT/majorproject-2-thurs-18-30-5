package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.security.JwtResponse;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import com.rmit.sept.assignment.initial.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.rmit.sept.assignment.initial.security.SecurityConstant.HEADER_NAME;

/**
 * Controller class to handle retrieving and updating of Worker entities
 */
@CrossOrigin
@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    private WorkerService workerService;

    @Autowired
    private AuthRequestService authService;

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
    public ResponseEntity<Worker> getWorker(@PathVariable Long id, @RequestHeader(value = HEADER_NAME, required = false) String token) {
        Worker worker = workerService.findById(id);
        // if the service return null this means no worker was found
        HttpStatus status = worker != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(worker, status);
    }

    /**
     * Endpoint to authenticate a worker based on username value. Also used for admin users by setting isAdmin flag
     * @param username username of worker
     * @param password password attempt
     * @param isAdmin admin flag
     * @return OK and Worker if valid otherwise null and NOT_FOUND/BAD_REQUEST
     */
    @GetMapping("/auth/username/{username}")
    public ResponseEntity<?> authenticateWorker(
            @PathVariable String username, @RequestParam String password, @RequestParam(required = false) Boolean isAdmin) {
        if (username != null && password != null) {
            Worker worker;
            HttpStatus status;
            JwtResponse response;
            if (isAdmin != null)
                worker = workerService.authenticateWorker(username, password, isAdmin);
            else
                worker = workerService.authenticateWorker(username, password, false);
            if (worker != null) {
                response = authService.getJwtResponse(username, password);
                status = response != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            } else {
                response = null;
                status = HttpStatus.NOT_FOUND;
            }
            return new ResponseEntity<>(response, status);
        } else {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Overloaded endpoint to authenticate a worker based on id value. Also used for admin users by setting isAdmin flag
     * @param id username of worker
     * @param password password attempt
     * @param isAdmin admin flag
     * @return OK and Worker if valid otherwise null and NOT_FOUND/BAD_REQUEST
     */
    @GetMapping("/auth/id/{id}")
    public ResponseEntity<?> authenticateWorker(
            @PathVariable Long id, @RequestParam String password, @RequestParam(required = false) Boolean isAdmin) {
        if (id != null && password != null) {
            Worker worker;
            if (isAdmin != null)
                worker = workerService.authenticateWorker(id, password, isAdmin);
            else
                worker = workerService.authenticateWorker(id, password, false);
            HttpStatus status = worker != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(worker, status);
        } else {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to return Workers for a Business. Optionally, start and end date-time arguments can be passed which will then
     * return only Workers for that Business who are not booked between those date-time values
     * @param businessId ID of Business to search
     * @param start LocalDateTime value for start of period - optional
     * @param end LocalDateTime value for end of period - optional
     * @return List of Worker objects
     */
    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<Worker>> getWorkersByBusiness(
            @RequestHeader(value = HEADER_NAME, required = false) String token,
            @PathVariable Long businessId,
            @RequestParam(required = false) Boolean isAdmin,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime end) {
//        if (authService.authGetBusinessEntitiesRequest(token, businessId)) {
            List<Worker> workers;
            if (start != null && end != null) {
                workers = workerService.findAllByBusiness(businessId, start, end, isAdmin);
            } else {
                workers = workerService.findAllByBusiness(businessId, isAdmin);
            }
            HttpStatus status = (workers == null) ? HttpStatus.BAD_REQUEST : (workers.size() > 0) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(workers, status);
//        }
//        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Endpoint to create a new Worker
     * @param worker Worker object to create
     * @param result field errors/validation based on Worker entity restrictions
     * @return Worker object and corresponding status code (CREATED or BAD_REQUEST)
     */
    @PostMapping("")
    public ResponseEntity<?> createNewWorker(@RequestHeader(value = HEADER_NAME, required = false) String token,
                                             @Validated @RequestBody Worker worker,
                                             BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {  // no invalid fields/entity errors
            if (authService.authWorkerRequest(token, worker)) {
                Worker worker1 = workerService.saveOrUpdateWorker(worker);
                HttpStatus status = (worker1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                return new ResponseEntity<Worker>(worker1, status);
            } else {
                return new ResponseEntity<String>("Unauthorised to perform request", HttpStatus.UNAUTHORIZED);
            }
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
    public ResponseEntity<?> updateWorker(@RequestHeader(value = HEADER_NAME, required = false) String token,
                                          @Validated @RequestBody Worker worker,
                                          BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {  // no invalid fields/entity errors
            Worker temp = workerService.findById(worker.getId());
            if (temp != null) {
                if (authService.authWorkerRequest(token, worker)) {
                    Worker worker1 = workerService.saveOrUpdateWorker(worker);
                    HttpStatus status = (worker1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                    return new ResponseEntity<Worker>(worker1, status);
                }
                return new ResponseEntity<>("Unauthorised to perform request", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>("Invalid Worker ID", HttpStatus.NOT_FOUND);
        }
        return errors;
    }
}