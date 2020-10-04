package com.rmit.sept.assignment.initial.repositories;

import com.rmit.sept.assignment.initial.model.Business;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends CrudRepository<Worker, Long> {
    @Override
    Iterable<Worker> findAllById(Iterable<Long> iterable);
    @Override
    Optional<Worker> findById(Long aLong);
    Optional<Worker> findByUserUsername(String username);
    Optional<Worker> findByIdAndIsAdmin(Long aLong, boolean admin);
    List<Worker> findAllByBusiness(Business business);
    List<Worker> findAllByBusiness_Id(Long businessId);
    List<Worker> findAllByBusiness_IdAndIsAdmin (Long businessId, Boolean isAdmin);
}
