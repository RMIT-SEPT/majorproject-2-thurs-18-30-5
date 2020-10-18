package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.Option;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

@SpringBootTest
@ActiveProfiles("test")
class WorkerServiceTest {
    @Autowired
    private WorkerService service;

    @MockBean
    private WorkerRepository repo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private HoursRepository hoursRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setup() {
        doReturn(true).when(encoder).matches(any(), any());
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
        User u1 = new User(null, "dondon94", "123Qwe!");  // should not create with null id
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

    private Worker setupFindByUsername() {
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        doReturn(Optional.of(w1)).when(repo).findByUserUsername(w1.getUser().getUsername());
        return w1;
    }

    @Test
    @DisplayName("Test findByUsername Success")
    void testFindByUsername() {
        Worker w1 = setupFindByUsername();
        Worker w2 = service.findByUsername(w1.getUser().getUsername());
        assertNotNull(w2);
        assertEquals(w2, w1);
    }

    @Test
    @DisplayName("Test authenticateWorkerUsername Null")
    void testFindByUsernameNull() {
        Worker w1 = setupFindByUsername();
        Worker w2 = service.findByUsername(null);
        assertNull(w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerUsername Not found")
    void testFindByUsernameNotFound() {
        Worker w1 = setupFindByUsername();
        Worker w2 = service.findByUsername("invalidusername");
        assertNull(w2);
    }

    private Worker setupFindById() {
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        doReturn(Optional.of(w1)).when(repo).findById(w1.getId());
        return w1;
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        Worker w1 = setupFindById();
        Worker w2 = service.findById(w1.getId());
        assertNotNull(w2);
        assertEquals(w1, w2);
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        Worker w1 = setupFindById();
        Worker w2 = service.findById(2L);  // worker id not in system
        assertNull(w2);
    }

    @Test
    @DisplayName("Test findById Null")
    void testFindByIdNull() {
        Worker w1 = setupFindById();
        Worker w2 = service.findById(null);  // worker id not in system
        assertNull(w2);
    }

    private Worker setupFindByIdAndAdmin() {
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        w1.setAdmin(true);
        doReturn(Optional.of(w1)).when(repo).findByIdAndIsAdmin(w1.getId(), w1.getAdmin());
        return w1;
    }

    @Test
    @DisplayName("Test authenticateWorkerId Success")
    void testAuthenticateWorkerId() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker(w1.getId(), w1.getUser().getPassword(), w1.getAdmin());
        assertNotNull(w2);
        assertEquals(w1, w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerId Null")
    void testAuthenticateWorkerIdNull() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker((Long) null, w1.getUser().getPassword(), w1.getAdmin());
        assertNull(w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerId Password Null")
    void testAuthenticateWorkerIdPasswordNull() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker(w1.getId(), null, w1.getAdmin());
        assertNull(w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerId Admin False")
    void testAuthenticateWorkerIdAdminFalse() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker(w1.getId(), w1.getUser().getPassword(), !w1.getAdmin());
        assertNull(w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerId Not Found")
    void testAuthenticateWorkerIdNotFound() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker(2L, w1.getUser().getPassword(), w1.getAdmin());
        assertNull(w2);
    }

    private Worker setupFindByUsernameAndAdmin() {
        Worker w1 = setupFindByIdAndAdmin();
        doReturn(Optional.of(w1)).when(repo).findByUserUsername(w1.getUser().getUsername());
        return w1;
    }

    @Test
    @DisplayName("Test authenticateWorkerUsername Success")
    void testAuthenticateWorkerUsername() {
        Worker w1 = setupFindByUsernameAndAdmin();
        Worker w2 = service.authenticateWorker(w1.getUser().getUsername(), w1.getUser().getPassword(), w1.getAdmin());
        assertNotNull(w2);
        assertEquals(w1, w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerUsername Username Null")
    void testAuthenticateWorkerUsernameNull() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker((String) null, w1.getUser().getPassword(), w1.getAdmin());
        assertNull(w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerUsername Password Null")
    void testAuthenticateWorkerUsernamePasswordNull() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker(w1.getUser().getUsername(), null, w1.getAdmin());
        assertNull(w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerUsername Admin False")
    void testAuthenticateWorkerUsernameAdminFalse() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker(w1.getUser().getUsername(), w1.getUser().getPassword(), !w1.getAdmin());
        assertNull(w2);
    }

    @Test
    @DisplayName("Test authenticateWorkerUsername Not Found")
    void testAuthenticateWorkerUsernameNotFound() {
        Worker w1 = setupFindByIdAndAdmin();
        Worker w2 = service.authenticateWorker("wrongusername", w1.getUser().getPassword(), w1.getAdmin());
        assertNull(w2);
    }

    private List<Worker> setupFindAllByBusiness(Long bid, boolean admin) {
        List<Worker> workerList = new ArrayList<>();
        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
        Worker w2 = new Worker(new User(2L, "samsam94", "123Qwe!"));
        w1.setAdmin(admin);
        w2.setAdmin(admin);
//        w1.setBookings(new ArrayList<>());
//        w2.setBookings(new ArrayList<>());
        workerList.add(w1);
        workerList.add(w2);
        doReturn(workerList).when(repo).findAllByBusiness_Id(bid);
        doReturn(workerList).when(repo).findAllByBusiness_IdAndIsAdmin(bid, admin);
        return workerList;
    }

    @Test
    @DisplayName("Test findAllByBusiness Success")
    void testFindAllByBusiness() {
        Long bid = 1L;
        List<Worker> workerList = setupFindAllByBusiness(bid, true);
        List<Worker> workerList1 = service.findAllByBusiness(bid, true);
        assertNotNull(workerList1);
        assertEquals(workerList.size(), workerList1.size());
    }

    @Test
    @DisplayName("Test findAllByBusiness Success Admin null")
    void testFindAllByBusinessAdminNull() {
        Long bid = 1L;
        List<Worker> workerList = setupFindAllByBusiness(bid, true);
        List<Worker> workerList1 = service.findAllByBusiness(bid, null);
        assertNotNull(workerList1);
        assertEquals(workerList.size(), workerList1.size());
    }

    @Test
    @DisplayName("Test findAllByBusiness Bid Null")
    void testFindAllByBusinessBidNull() {
        Long bid = 1L;
        List<Worker> workerList = setupFindAllByBusiness(bid, true);
        List<Worker> workerList1 = service.findAllByBusiness(null, true);
        assertNull(workerList1);
    }

    @Test
    @DisplayName("Test findAllByBusiness Not Found")
    void testFindAllByBusinessNotFound() {
        Long bid = 1L;
        List<Worker> workerList = setupFindAllByBusiness(bid, true);
        List<Worker> workerList1 = service.findAllByBusiness(2L, true);
        assertNotNull(workerList1);
        assertEquals(0, workerList1.size());
    }

    @Test
    @DisplayName("Test findAllByBusiness Admin False")
    void testFindAllByBusinessAdminFalse() {
        Long bid = 1L;
        List<Worker> workerList = setupFindAllByBusiness(bid, true);
        List<Worker> workerList1 = service.findAllByBusiness(bid, false);
        assertNotNull(workerList1);
        assertEquals(0, workerList1.size());
    }

    private void setupFindHoursByWorker(List<Worker> workerList, LocalTime start, LocalTime end, DayOfWeek skip) {
        Hours temp;
        for (Worker worker : workerList) {
            List<Hours> hoursList = new ArrayList<>();
            doReturn(Optional.of(worker)).when(repo).findById(worker.getId());
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                if (skip == null || skip.compareTo(dayOfWeek) != 0) {
                    temp = new Hours(dayOfWeek, worker);
                    temp.setStart(start);
                    temp.setEnd(end);
                    hoursList.add(temp);
                }
            }
            doReturn(hoursList).when(hoursRepository).findById_WorkerId(worker.getId());
        }
    }

    @Test
    @DisplayName("Test findAllByBusinessDates Success")
    void testFindAllByBusinessDates() {
        Long bid = 1L;
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("17:00");
        // 2021-01-01 is a FRIDAY
        LocalDateTime dtStart = LocalDateTime.of(2021, 1, 1, 10, 0);
        LocalDateTime dtEnd= LocalDateTime.of(2021, 1, 1, 11, 0);
        List<Worker> workerList = setupFindAllByBusiness(bid, false);
        setupFindHoursByWorker(workerList, start, end, null);
        List<Worker> availList = service.findAllByBusiness(bid, dtStart, dtEnd, false);
        assertNotNull(availList);
        assertEquals(workerList.size(), availList.size());
    }

    @Test
    @DisplayName("Test findAllByBusinessDates Unavailable")
    void testFindAllByBusinessDatesUnavailable() {
        Long bid = 1L;
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("17:00");
        // 2021-01-01 is a FRIDAY
        LocalDateTime dtStart = LocalDateTime.of(2021, 1, 1, 10, 0);
        LocalDateTime dtEnd= LocalDateTime.of(2021, 1, 1, 11, 0);
        List<Worker> workerList = setupFindAllByBusiness(bid, false);
        setupFindHoursByWorker(workerList, start, end, DayOfWeek.FRIDAY);
        List<Worker> availList = service.findAllByBusiness(bid, dtStart, dtEnd, false);
        assertNotNull(availList);
        assertEquals(0, availList.size());
    }

    @Test
    @DisplayName("Test findAllByBusinessDates Bid Null")
    void testFindAllByBusinessDatesBidNull() {
        Long bid = 1L;
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("17:00");
        // 2021-01-01 is a FRIDAY
        LocalDateTime dtStart = LocalDateTime.of(2021, 1, 1, 10, 0);
        LocalDateTime dtEnd= LocalDateTime.of(2021, 1, 1, 11, 0);
        List<Worker> workerList = setupFindAllByBusiness(bid, false);
        setupFindHoursByWorker(workerList, start, end, null);
        List<Worker> availList = service.findAllByBusiness(null, dtStart, dtEnd, false);
        assertNull(availList);
    }

    @Test
    @DisplayName("Test findAllByBusinessDates Start Null")
    void testFindAllByBusinessDatesStartNull() {
        Long bid = 1L;
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("17:00");
        // 2021-01-01 is a FRIDAY
        LocalDateTime dtEnd= LocalDateTime.of(2021, 1, 1, 11, 0);
        List<Worker> workerList = setupFindAllByBusiness(bid, false);
        setupFindHoursByWorker(workerList, start, end, null);
        List<Worker> availList = service.findAllByBusiness(bid, null, dtEnd, false);
        assertNull(availList);
    }

    @Test
    @DisplayName("Test findAllByBusinessDates Admin False")
    void testFindAllByBusinessDatesAdmin() {
        Long bid = 1L;
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("17:00");
        // 2021-01-01 is a FRIDAY
        LocalDateTime dtStart = LocalDateTime.of(2021, 1, 1, 10, 0);
        LocalDateTime dtEnd= LocalDateTime.of(2021, 1, 1, 11, 0);
        List<Worker> workerList = setupFindAllByBusiness(bid, false);
        setupFindHoursByWorker(workerList, start, end, null);
        List<Worker> availList = service.findAllByBusiness(bid, dtStart, dtEnd, true);
        assertNotNull(availList);
        assertEquals(0, availList.size());
    }
}