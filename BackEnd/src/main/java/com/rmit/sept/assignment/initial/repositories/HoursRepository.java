package com.rmit.sept.assignment.initial.repositories;

import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface HoursRepository extends CrudRepository<Hours, Hours.HoursPK> {
    Optional<Hours> findById(Hours.HoursPK hoursPK);
    List<Hours> findById_Worker(Worker worker);
    List<Hours> findById_WorkerId(Long workerId);
    List<Hours> findAll(Example<Hours> hours);
    void deleteById(Hours.HoursPK hoursPK);
    void deleteById_WorkerAndId_DayOfWeek(Worker id_worker, DayOfWeek id_dayOfWeek);
}
