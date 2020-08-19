package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.Repositories.HoursRepository;
import com.rmit.sept.assignment.initial.Repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.HoursPK;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (hours.getId() == null) return null;
        Date start = hours.getStart();
        Date end = hours.getEnd();
        if (!(start == null || end == null)) {
            Calendar calStart = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();
            calStart.setTime(start);
            calEnd.setTime(end);
            boolean sameDay = (calEnd.get(Calendar.DAY_OF_YEAR) == calStart.get(Calendar.DAY_OF_YEAR)) &&
                    (calEnd.get(Calendar.YEAR) == calStart.get(Calendar.YEAR));
            if (!sameDay || calStart.after(calEnd)) return null;
        }
        return hoursRepository.save(hours);
    }

    public Hours findById(Worker worker, Long dayOfWeek) {
        return hoursRepository.findById(new Hours.HoursPK(worker, dayOfWeek)).orElse(null);
    }

    public List<Hours> findByWorker(Worker worker) {
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
