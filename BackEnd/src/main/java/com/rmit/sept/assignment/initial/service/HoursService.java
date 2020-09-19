package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HoursService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    HoursRepository hoursRepository;

    public Hours saveOrUpdateHours(Hours hours) {
        Hours.HoursPK hoursId = hours.getId();
        if (hoursId == null) return null;  // must have a valid id
        Worker worker = hoursId.getWorker();
        if (worker == null) return null;  // worker object must exist (at least need id)
        Long workerId = worker.getId();
        if (workerId == null) return null;  // must have an id value
        Optional<Worker> existingWorker = workerRepository.findById(workerId);
        if (!existingWorker.isPresent()) return null;  // worker must already exist in the system
        LocalTime start = hours.getStart();
        LocalTime end = hours.getEnd();
        if (!(start == null || end == null)) {
            if (start.compareTo(end) >= 0) return null;
        }
        return hoursRepository.save(hours);
    }

    public boolean deleteHours(Hours hours) {
        if (hours == null || hours.getId() == null) return false;
        Optional<Hours> h1 = hoursRepository.findById(hours.getId());
        if (h1.isPresent()) {
            hoursRepository.deleteById(hours.getId());
            return !hoursRepository.findById(hours.getId()).isPresent();  // return true if hours have been removed
        }
        return false;
    }

    public boolean deleteHours(Long workerId, DayOfWeek dayOfWeek) {
        Optional<Worker> worker = workerRepository.findById(workerId);
        if (worker.isPresent()) {
            Hours.HoursPK hoursPK = new Hours.HoursPK(worker.get(), dayOfWeek);
            Optional<Hours> hours = hoursRepository.findById(hoursPK);
            if (hours.isPresent()) {
                hoursRepository.delete(hours.get());
                return !hoursRepository.findById(hoursPK).isPresent();  // return true if hours have been removed
            }
        }
        return false;
    }

    public Hours findById(Hours.HoursPK hoursPK) {
        return (hoursPK != null) ? hoursRepository.findById(hoursPK).orElse(null) : null;
    }

    public Hours findById(Worker worker, DayOfWeek dayOfWeek) {
        if (worker == null || dayOfWeek == null) return null;
        return hoursRepository.findById(new Hours.HoursPK(worker, dayOfWeek)).orElse(null);
    }

    public List<Hours> findByWorker(Worker worker) {
        if (worker == null) return null;
        return hoursRepository.findById_Worker(worker);
    }
}
