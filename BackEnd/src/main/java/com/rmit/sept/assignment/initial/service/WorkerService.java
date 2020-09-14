package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Service method to update or create a worker.
     * This requires a valid user to exist (worker.id references user.id)
     * @param worker Worker object to create or update
     * @return Worker object, or null if invalid (no id)
     */
    public Worker saveOrUpdateWorker(Worker worker) {
        Long workerId = worker.getId();
        if (workerId != null) {  // workerId cannot be null
            Optional<Worker> worker1 = workerRepository.findById(workerId);
            if (worker1.isPresent()) {  // UPDATE WORKER
                worker.setUser(worker1.get().getUser());
                return workerRepository.save(worker);
            }
            Optional<User> user = userRepository.findById(workerId);
            if (user.isPresent()) {  // CREATE WORKER i.e. user has been created but worker has not
                worker.setUser(user.get());
                return workerRepository.save(worker);
            }
        }
        return null;
    }

//    public Worker updateWorker(Worker worker) {
//        Optional<Worker> worker1 = workerRepository.findById(worker.getId());
//        if (worker1.isPresent()) {
//            return workerRepository.save(worker1.get());
//        } else {
//            return null;
//        }
//        Optional<Worker> worker1 = workerRepository.findById(worker.getId());
//
//
//        if (worker1.isPresent()) {
//            return worker1.map(value -> workerRepository.save(value)).orElseGet(() -> workerRepository.save(worker));
//        } else {
//            Optional<User> user = userRepository.findById(worker.getId());
//            if (user.isPresent()) {
//                return workerRepository.save(new Worker(user.get()));
//            } else {
//                return workerRepository.save(new Worker(worker.getUser()));
//            }
//        }
//    }

    /**
     * Returns all workers
     * @return an ArrayList of Worker objects
     */
    public Collection<Worker> findAll() {
        List<Worker> workers = new ArrayList<>();
        workerRepository.findAll().forEach(workers::add);
        return workers;
    }

    /**
     * Returns a Worker object based on id value
     * @param id id of Worker to fetch
     * @return Worker object if found, otherwise null
     */
    public Worker findById(Long id) {
        Optional<Worker> worker = workerRepository.findById(id);
        return worker.orElse(null);
    }
}
