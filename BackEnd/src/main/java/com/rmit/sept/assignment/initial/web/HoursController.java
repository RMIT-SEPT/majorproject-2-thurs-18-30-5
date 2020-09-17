package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import com.rmit.sept.assignment.initial.service.HoursService;
import com.rmit.sept.assignment.initial.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/hours")
public class HoursController {
    @Autowired
    private HoursService hoursService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private FieldValidationService validationService;

    @GetMapping(value = "/{workerId}")
    public ResponseEntity<Collection<Hours>> getWorkerHours(@PathVariable Long workerId) {
        Worker worker = workerService.findById(workerId);
        if (worker != null) {
            Collection<Hours> workerHours = hoursService.findByWorker(worker);
            return new ResponseEntity<>(workerHours, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

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

    @PostMapping(value = "/{workerId}")
    public ResponseEntity<?> createNewHours(@PathVariable Long workerId, @Validated @RequestBody Hours hours, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null && workerId != null) {
            Hours hours1 = null;
            Worker w1 = workerService.findById(workerId);
            if (w1 != null) {
                Hours.HoursPK pk = hours.getId();
                pk.setWorker(w1);
                hours.setId(pk);
                hours1 = hoursService.saveOrUpdateHours(hours);
            }
            HttpStatus status = (hours1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
            return new ResponseEntity<>(hours1, status);
        } else {
            return errors;
        }
    }

    @DeleteMapping(value = "/{workerId}", params = "dayOfWeek")
    public ResponseEntity<?> deleteWorkerHours(@PathVariable Long workerId, @RequestParam DayOfWeek dayOfWeek) {
        boolean deleteHours = false;
        if (workerId != null && dayOfWeek != null) {
            deleteHours = hoursService.deleteHours(workerId, dayOfWeek);
        }
        HttpStatus status = (deleteHours) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>("Hours deleted", status);
    }
}
