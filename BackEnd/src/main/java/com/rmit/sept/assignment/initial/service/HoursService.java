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
//            Calendar calStart = Calendar.getInstance();
//            Calendar calEnd = Calendar.getInstance();
//            calStart.setTime(start);
//            calEnd.setTime(end);
//            boolean sameDay = (calEnd.get(Calendar.DAY_OF_YEAR) == calStart.get(Calendar.DAY_OF_YEAR)) &&
//                    (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR));
//            if (!sameDay || calStart.after(calEnd)) return null;
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
                hoursRepository.deleteById(hoursPK);
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
//
//    public Hours createHours(Hours hours) {
//        System.out.println(hours);
//        Hours h1 = hoursRepository.save(hours);
//        System.out.println(h1);
//        return h1;
//    }
//
//    public Hours findById(Worker worker, int dayOfWeek) {
//        if (worker == null || dayOfWeek <= -1 || dayOfWeek > 6) return null;
//        return hoursRepository.findById(new HoursPK(worker.getId(), dayOfWeek)).orElse(null);
//    }
//
//    public List<Hours> findByWorker(Worker worker) {
//        if (worker == null) return null;
//        return hoursRepository.findAllByWorker(worker);
//    }

//
//    public Hours createHours(Hours hours) {
//        if (hours.getWorker() == null) return null;
//        Long workerId = hours.getWorker().getId();
//        System.out.println("WORKERID " + workerId);
//        Optional<Worker> worker = workerRepository.findById(workerId);
//        if (worker.isPresent()) {
//            System.out.println("PRESENT");
//            System.out.println(hours.getStart() + " " + hours.getEnd());
//            Hours h2 =  hoursRepository.save(hours);
//            System.out.println(h2);
//            return h2;
////            if (!hoursRepository.findByWorkerAndAndDayOfWeek(worker.get().getId(), hours.getDayOfWeek()).isPresent()) {
////            }
//        }
//        return null;
//    }
//
//    public Hours findById(Long id) {
//        Optional<Hours> hours =  hoursRepository.findById(id);
//        return hours.orElse(null);
//    }
}
