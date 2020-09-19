package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit testing for HoursService
 */
@SpringBootTest
@ActiveProfiles("test")
class HoursServiceTest {
    @Autowired
    private HoursService service;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private UserService userService;
    @MockBean
    private HoursRepository repo;
    @MockBean
    private WorkerRepository workerRepository;


    private Hours h1 = null;
    private Worker w1 = null;
    private User u1 = null;

    @BeforeEach
    void setUpTest() {
        u1 = new User(1L, "dondon94", "123Qwe!");
        w1 = new Worker(u1);
        User temp1 = userService.saveOrUpdateUser(u1);
        Worker temp2 = workerService.saveOrUpdateWorker(w1);
        doReturn(Optional.of(w1)).when(workerRepository).findById(any());
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, DayOfWeek.FRIDAY);
        h1 = new Hours();
        h1.setId(hoursPK);
        h1.setStart("09:00");
        h1.setEnd("17:00");
        doReturn(h1).when(repo).save(h1);
        System.out.println("*****************" +
                "ADDING USERS"+
                "*****************" +
                "\nuser and worker created = " + (temp1 != null && temp2 != null) + "\n");
    }

    @AfterEach
    void removeHours() {
        if (h1 != null) {
            service.deleteHours(h1);
            Hours temp = service.findById(h1.getId());
            System.out.println("*****************" +
                    "CLEAN UP" +
                    "*****************" +
                    "\nhours removed = " + (temp == null) + "\n");
        }
    }

    @Test
    @DisplayName("Test createHours Success")
    void testCreateHours() {
        Hours h2 = service.saveOrUpdateHours(h1);  // we are testing this bit
        assertNotNull(h2);
        assertEquals(h1, h2);
    }

    @Test
    @DisplayName("Test createHours Invalid Hours")
    void testCreateHoursInvalidHours() {
        h1.setStart(LocalTime.parse("17:00"));  // test with start after end
        h1.setEnd(LocalTime.parse("09:00"));  // test with end after start
        Hours h2 = service.saveOrUpdateHours(h1);
        assertNull(h2);
    }

    @Test
    @DisplayName("Test findByWorker Success")
    void testFindByWorker() {
        doReturn(Arrays.asList(new Hours(), new Hours())).when(repo).findById_WorkerId(any());  // return a list of 2
        doReturn(Arrays.asList(new Hours(), new Hours())).when(repo).findById_Worker(any());  // return list of 2

        List<Hours> hours = service.findByWorker(w1);  // we are testing this bit - if search works

        assertNotNull(hours);
        assertEquals(2, hours.size());  // should equal existing size of hours + 1
    }

    @Test
    @DisplayName("Test findByWorker Failure - no Hours")
    void testFindByWorkerNoHours() {
        Worker w2 = new Worker(new User(2L, "anotheruser", "123Qwe!"));
        doReturn(Arrays.asList(new Hours(), new Hours())).when(repo).findById_WorkerId(w1.getId()); //only for w1
        doReturn(Arrays.asList(new Hours(), new Hours())).when(repo).findById_Worker(w1);  // only for w1

        List<Hours> hours = service.findByWorker(w2);  // we are testing this bit - if search works

        assertEquals(0, hours.size());  // should return an empty list
    }

    @Test
    @DisplayName("Test findByWorker Failure - Null Worker")
    void testFindByWorkerNull() {
        List<Hours> hours = service.findByWorker(null);  // we are testing this bit - if search works
        assertNull(hours);
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, DayOfWeek.FRIDAY);
        h1 = new Hours();
        h1.setId(hoursPK);
        doReturn(Optional.of(h1)).when(repo).findById(any());

        Hours h2 = service.findById(w1, hoursPK.getDayOfWeek());

        assertNotNull(h2);
        assertEquals(h1, h2);
    }

    @Test
    @DisplayName("Test updateHours Success")
    void testUpdateHours() {
        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        assertNotNull(h2);
        h2.setStart("10:00");
        h2.setEnd("18:00");

        Hours h3 = service.saveOrUpdateHours(h2);  // update existing hours

        assertNotNull(h3);
        assertEquals(h2.getStart(), h3.getStart());  // check that updated values correctly
        assertEquals(h2.getEnd(), h3.getEnd());  // check that updated values correctly
    }

    @Test
    @DisplayName("Test deleteHours Success")
    void testDeleteHours() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, DayOfWeek.SATURDAY);
        h1 = new Hours();
        h1.setStart("09:00");
        h1.setEnd("17:00");
        h1.setId(hoursPK);
        doReturn(h1).when(repo).save(h1);
        when(repo.findById(h1.getId())).thenReturn(Optional.of(h1)).thenReturn(Optional.empty());

        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        assertNotNull(h2);
        assertEquals(h1, h2);

        assertTrue(service.deleteHours(h2));  // test delete hours - service method calls findById to confirm removal

        Hours h3 = service.findById(h2.getId());
        assertNull(h3);  // double check that have been removed
    }
}