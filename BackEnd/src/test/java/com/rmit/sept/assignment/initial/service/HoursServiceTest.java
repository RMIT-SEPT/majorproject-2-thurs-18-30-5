package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for HoursService
 */
@SpringBootTest
class HoursServiceTest {
    @Autowired
    private HoursService service;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private UserService userService;

//    @MockBean
    private HoursRepository repo;

    private final Calendar cal = Calendar.getInstance();

    private Hours h1;
    private Worker w1;
    private User u1;

    @BeforeEach
    void setUpTest() {
        u1 = new User(1L, "dondon94", "123Qwe!");
        w1 = new Worker(u1);
        User temp1 = userService.saveOrUpdateUser(u1);
        Worker temp2 = workerService.saveOrUpdateWorker(w1);
        System.out.println("*****************" +
                "ADDING USERS"+
                "*****************" +
                "\nuser and worker created = " + (temp1 != null && temp2 != null) + "\n");
    }

    @AfterEach
    void removeHours() {
        service.deleteHours(h1);
        Hours temp = service.findById(h1.getId());
        System.out.println("*****************" +
                "CLEAN UP"+
                "*****************" +
                "\nhours removed = "  + (temp == null) + "\n");
    }

    @Test
    @DisplayName("Test createHours Success")
    void testCreateHours() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        h1 = new Hours();
        h1.setId(hoursPK);

        userService.saveOrUpdateUser(u1);
        workerService.saveOrUpdateWorker(w1);

        Hours h2 = service.saveOrUpdateHours(h1);  // we are testing this bit
        assertNotNull(h2);
        assertEquals(h1, h2);
    }

    @Test
    @DisplayName("Test createHours Invalid Hours")
    void testCreateHoursInvalidHours() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        h1 = new Hours();
        h1.setId(hoursPK);

        cal.set(2020, Calendar.FEBRUARY, 1);
        h1.setStart(cal.getTime());
        cal.set(2020, Calendar.FEBRUARY, 2);
        h1.setEnd(cal.getTime());

        Hours h2 = service.saveOrUpdateHours(h1);

        assertNull(h2);
    }

    @Test
    @DisplayName("Test findByWorker Success")
    void testFindByWorker() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        h1 = new Hours();
        h1.setId(hoursPK);

        List<Hours> hours = service.findByWorker(w1);  // we are testing this bit - if search works
        int size = hours.size();
        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        hours = service.findByWorker(w1);  // we are testing this bit - if search works

        assertNotNull(hours);
        assertEquals(size+1, hours.size());  // should equal existing size of hours + 1
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        h1 = new Hours();
        h1.setId(hoursPK);

        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        Hours h3 = service.findById(w1, hoursPK.getDayOfWeek());

        assertNotNull(h3);
        assertEquals(h1, h3);
    }

    @Test
    @DisplayName("Test updateHours Success")
    void testUpdateHours() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        h1 = new Hours();
        cal.set(2020, Calendar.FEBRUARY, 1, 10, 0);
        h1.setStart(cal.getTime());
        cal.set(2020, Calendar.FEBRUARY, 1, 11, 0);
        h1.setEnd(cal.getTime());
        h1.setId(hoursPK);

        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        assertNotNull(h2);
        cal.set(2020, Calendar.JANUARY, 2, 10, 0);
        Date d1 = cal.getTime();
        h2.setStart(d1);
        cal.set(2020, Calendar.JANUARY, 2, 11, 0);
        Date d2 = cal.getTime();
        h2.setEnd(d2);

        Hours h4 = service.saveOrUpdateHours(h2);  // update existing hours

        assertNotNull(h4);
        assertEquals(d1, h4.getStart());
        assertEquals(d2, h4.getEnd());
        assertNotEquals(h1.getStart(), h4.getStart());
        assertNotEquals(h1.getEnd(), h4.getEnd());
    }

    @Test
    void testDeleteHours() {
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        h1 = new Hours();
        cal.set(2020, Calendar.FEBRUARY, 1, 10, 0);
        h1.setStart(cal.getTime());
        cal.set(2020, Calendar.FEBRUARY, 1, 11, 0);
        h1.setEnd(cal.getTime());
        h1.setId(hoursPK);

        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        assertNotNull(h2);
        assertEquals(h1, h2);

        assertTrue(service.deleteHours(h2));  // test delete hours - service method calls findById to confirm removal

        Hours h3 = service.findById(h2.getId());
        assertNull(h3);  // double check that have been removed
    }

//    @Test
//    void testFindById() {
//        h1 = new Hours(new HoursPK(w1.getId(), 0), new Date(), new Date());
//        doReturn(Optional.of(h1)).when(repo).findByWorkerAndDayOfWeek(w1, 0);
//        Hours h2 = service.findById(w1, 0);
//        assertNotNull(h2);
//    }
//
//    @Test
//    void testFindByIdTwo() {
//        h1 = new Hours(new HoursPK(w1.getId(), 0), new Date(), new Date());
//        service.createHours(h1);
////        doReturn(Optional.of(h1)).when(repo).findByWorkerAndDayOfWeek(w1, 0);
//        List<Hours> h2 = service.findByWorker(w1);
//        assertNotNull(h2);
//    }
//
//    @Test
//    void testFindByWorker() {
//        doReturn(Arrays.asList(new Hours(), new Hours(), new Hours())).when(repo).findAllByWorker(any());
//        List<Hours> hours = service.findByWorker(w1);
//        assertNotNull(hours);
//        assertEquals(hours.size(), 3);
//    }

//
//    @Test
//    @DisplayName("Test createHours success")
//    void testCreateHours() {
//        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
//        h1 = new Hours(w1, 0, new Date(), new Date());
//        System.out.println(h1.getId());
//        doReturn(Optional.of(w1)).when(workerRepo).findById(any());
//
//        Hours h2 = service.createHours(h1);
//
//        System.out.println(h2);
//        assertNotNull(h2);
//
//        assertEquals(h1, h2);
//    }
//
//    @Test
//    @DisplayName("Test getHours success")
//    void testGetHours() {
//        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
//        h1 = new Hours(w1, 0, new Date(), new Date());
//        doReturn(Optional.of(h1)).when(repo).findById(any());
//
//        Hours h2 = service.findById(1L);
//
//        assertNotNull(h2);
//    }
//
//    @Test
//    void testGetHoursByWorkerAndDOW() {
//        Worker w1 = new Worker(new User(1L, "dondon94", "123Qwe!"));
//        h1 = new Hours(w1, 0, new Date(), new Date());
////        doReturn(Optional.of(h1)).when(repo).findByWorkerAndDayOfWeek(w1, 0);
//
//        Hours h2 = service.findByWorkerDOW(w1, 0);
//
//        assertNotNull(h2);
//    }
}