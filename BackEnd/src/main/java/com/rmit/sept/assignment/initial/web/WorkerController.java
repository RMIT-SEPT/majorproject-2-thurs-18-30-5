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

    @GetMapping("")
    public ResponseEntity<Collection<Worker>> getWorkers() {
        return new ResponseEntity<>(workerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Long id) {
        Worker worker = workerService.findById(id);
        HttpStatus status = worker != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(worker, status);
    }

    @PostMapping("")
    public ResponseEntity<?> createNewWorker(@Validated @RequestBody Worker worker, BindingResult result) {
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                return new ResponseEntity<List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            }
        }
        Worker worker1 = workerService.saveOrUpdateWorker(worker);
        HttpStatus status = (worker1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
        return new ResponseEntity<Worker>(worker1, status);
    }
}