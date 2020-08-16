package com.rmit.sept.assignment.initial.Repositories;

import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker, Long> {
    @Override
    Iterable<Worker> findAllById(Iterable<Long> iterable);
}
