package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.Repositories.HoursRepository;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private Calendar cal = Calendar.getInstance();

    @Test
    @DisplayName("Test createHours Success")
    void testCreateHours() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        Hours h1 = new Hours();
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
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        Hours h1 = new Hours();
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
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        Hours h1 = new Hours();
        h1.setId(hoursPK);

        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        List<Hours> hours = service.findByWorker(w1);  // we are testing this bit - if search works

        assertNotNull(hours);
        assertEquals(1, hours.size());
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        Hours h1 = new Hours();
        h1.setId(hoursPK);

        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db

        Hours h3 = service.findById(w1, hoursPK.getDayOfWeek());

        assertNotNull(h3);
        assertEquals(h1, h3);
    }

    @Test
    @DisplayName("Test updateHours Success")
    void testUpdateHours() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        Worker w1 = new Worker(u1);
        Hours.HoursPK hoursPK = new Hours.HoursPK(w1, 0L);
        Hours h1 = new Hours();
        cal.set(2020, Calendar.FEBRUARY, 1);
        h1.setStart(cal.getTime());
        cal.set(2020, Calendar.FEBRUARY, 2);
        h1.setEnd(cal.getTime());
        h1.setId(hoursPK);

        Hours h2 = service.saveOrUpdateHours(h1);  // add hours to db
        assertNotNull(h2);
        cal.set(2020, Calendar.JANUARY, 1);
        Date d1 = cal.getTime();
        h2.setStart(d1);
        cal.set(2020, Calendar.JANUARY, 2);
        Date d2 = cal.getTime();
        h2.setEnd(d2);
        Hours h4 = service.saveOrUpdateHours(h2);
        assertNotNull(h4);
        assertEquals(d1, h4.getStart());
        assertEquals(d2, h4.getEnd());
        assertNotEquals(h1.getStart(), h4.getStart());
        assertNotEquals(h1.getEnd(), h4.getEnd());
    }

//    @Test
//    void testFindById() {
//        Hours h1 = new Hours(new HoursPK(w1.getId(), 0), new Date(), new Date());
//        doReturn(Optional.of(h1)).when(repo).findByWorkerAndDayOfWeek(w1, 0);
//        Hours h2 = service.findById(w1, 0);
//        assertNotNull(h2);
//    }
//
//    @Test
//    void testFindByIdTwo() {
//        Hours h1 = new Hours(new HoursPK(w1.getId(), 0), new Date(), new Date());
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
//        Hours h1 = new Hours(w1, 0, new Date(), new Date());
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
//        Hours h1 = new Hours(w1, 0, new Date(), new Date());
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
//        Hours h1 = new Hours(w1, 0, new Date(), new Date());
////        doReturn(Optional.of(h1)).when(repo).findByWorkerAndDayOfWeek(w1, 0);
//
//        Hours h2 = service.findByWorkerDOW(w1, 0);
//
//        assertNotNull(h2);
//    }
}