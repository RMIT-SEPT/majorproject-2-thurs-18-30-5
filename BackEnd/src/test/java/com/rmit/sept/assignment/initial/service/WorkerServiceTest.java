package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.Repositories.UserRepository;
import com.rmit.sept.assignment.initial.Repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

@SpringBootTest
class WorkerServiceTest {
    @Autowired
    private WorkerService service;

    @MockBean
    private WorkerRepository repo;

    @MockBean
    private UserRepository userRepo;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        doReturn(Optional.of(w1)).when(repo).findById(1L);

        Worker w2 = service.findById(1L);

        assertNotNull(w2);
        assertSame(w1, w2);
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        doReturn(Optional.of(w1)).when(repo).findById(1L);

        Worker w2 = service.findById(2L);

        assertNull(w2);
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        Worker w2 = new Worker(new User(2L, "samsam98", "123Qwe!"));
        Worker w3 = new Worker(new User(3L, "mary123", "123Qwe!"));

        doReturn(Arrays.asList(w1, w2, w3)).when(repo).findAll();

        Collection<Worker> workers = service.findAll();
        assertEquals(workers.size(), 3);

        doReturn(Collections.emptyList()).when(repo).findAll();

        workers = service.findAll();
        assertEquals(workers.size(), 0);
    }

    @Test
    @DisplayName("Test saveOrUpdateWorker Create Success")
    void testCreateWorker() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        doReturn(w1).when(repo).save(any());
        doReturn(Optional.of(u1)).when(userRepo).findById(any());

        Worker w2 = service.saveOrUpdateWorker(w1);

        assertNotNull(w2);
        assertEquals(w1, w2);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Bad Request")
    void testCreateUserBadRequest() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        doReturn(w1).when(repo).save(any());
        doReturn(Optional.of(u1)).when(userRepo).findById(any());

        Worker w2 = service.saveOrUpdateWorker(new Worker());  // test with no id

        assertNull(w2);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Bad Request - no user record")
    void testCreateUserBadRequestNoUser() {
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        doReturn(w1).when(repo).save(any());
        doReturn(Optional.empty()).when(userRepo).findById(any());  // test create when no user found in system

        Worker w2 = service.saveOrUpdateWorker(w1);

        assertNull(w2);
    }

    @Test
    @DisplayName("Test saveOrUpdateWorker Create Null")
    void testCreateWorkerNull() {
        User u1 = new User(null, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        doReturn(w1).when(repo).save(any());
        doReturn(Optional.of(u1)).when(userRepo).findById(any());

        Worker w2 = service.saveOrUpdateWorker(w1);

        assertNull(w2);
    }

    @Test
    @DisplayName("Test saveOrUpdateWorker Update Success")
    void testUpdateWorker() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        doReturn(w1).when(repo).save(any());
        doReturn(Optional.of(u1)).when(userRepo).findById(any());

        Worker w2 = service.saveOrUpdateWorker(w1);

        // confirm create worker worked
        assertNotNull(w2);
        assertEquals(w1, w2);

        w1.setAdmin(true);
        doReturn(Optional.of(w1)).when(repo).findById(any());

        Worker w3 = service.saveOrUpdateWorker(w1);

        // confirm that update isAdmin has worked
        assertNotNull(w3);
        assertTrue(w3.getAdmin());
    }

    @Test
    @DisplayName("Test saveOrUpdateWorker Update Null")
    void testUpdateWorkerNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        doReturn(w1).when(repo).save(any());
        doReturn(Optional.of(u1)).when(userRepo).findById(any());

        Worker w2 = service.saveOrUpdateWorker(w1);

        // confirm create worker worked
        assertNotNull(w2);
        assertEquals(w1, w2);

        w1.setUser(null);  // service should not update worker with a null id / user
        doReturn(Optional.of(w1)).when(repo).findById(any());

        Worker w3 = service.saveOrUpdateWorker(w1);

        assertNotNull(w3);  // confirm that worker is not updated
    }
}