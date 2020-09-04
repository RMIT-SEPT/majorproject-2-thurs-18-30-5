package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.repositories.BookingRepository;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import mockit.MockUp;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.rmi.CORBA.Util;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

@SpringBootTest
class BookingServiceTest {
    @Autowired
    private BookingService service;

    @MockBean
    private BookingRepository repo;
//    @Autowired
//    private BookingRepository repo;
    @MockBean
    private UserRepository userRepo;
    @MockBean
    private WorkerRepository workerRepo;
    @MockBean
    private Utilities utilities;


    private final Worker w1 = new Worker(new User(1L, "testuser", "123Qwe!"));
    private final User u1 = new User(2L, "testcust", "123Qwe!");
    private final Calendar start = Calendar.getInstance();
    private final Calendar end = Calendar.getInstance();
//
//    @Test
//    @DisplayName("Test saveOrUpdate Success No Id")
//    void testCreateBookingNoId() {
////        Calendar cal = Calendar.getInstance();
//        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
//        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);
//
//        Booking b1 = new Booking();
//        b1.setStart(start.getTime());
//        b1.setEnd(end.getTime());
//
//        b1.setWorker(w1);
//        b1.setUser(u1);
//
//        doReturn(Optional.of(w1)).when(workerRepo).findById(1L);
//        doReturn(Optional.empty()).when(workerRepo).findById(2L);
//        doReturn(Optional.of(u1)).when(userRepo).findById(2L);
//
//        Booking b2 =service.saveOrUpdateBooking(b1);
//        assertEquals(13L, b2.getId());
//    }

    @Test
    @DisplayName("Test saveOrUpdate Success")
    void testCreateBooking() {
//        Calendar cal = Calendar.getInstance();
        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);

        Booking b1 = new Booking(1L);
        b1.setStart(start.getTime());
        b1.setEnd(end.getTime());

        b1.setWorker(w1);
        b1.setUser(u1);

        doReturn(Optional.of(w1)).when(workerRepo).findById(1L);
        doReturn(Optional.empty()).when(workerRepo).findById(2L);
        doReturn(Optional.of(u1)).when(userRepo).findById(2L);
        doReturn(b1).when(repo).save(any());
        doReturn(new ArrayList<>()).when(repo).findAllByUser_Id(any());  // empty list => no overlap
        doReturn(new ArrayList<>()).when(repo).findAllByWorker_Id(any());  // empty list => no overlap

        Booking b2 = service.saveOrUpdateBooking(b1);

        assertNotNull(b2);
        assertEquals(b1.getId(), b2.getId());

        start.set(2020, Calendar.FEBRUARY, 2, 9, 0);
        b2.setStart(start.getTime());
        doReturn(b2).when(repo).save(any());

        Booking b3 = service.saveOrUpdateBooking(b2);
        assertNotNull(b3);
        assertEquals(b2.getId(), b3.getId());
        assertEquals(b2.getStart(), b3.getStart());
    }

    @Test
    @DisplayName("Test findOverlap Success")
    void testFindOverlap() {
        List<Booking> bookings = new ArrayList<>();
        start.set(2020, Calendar.FEBRUARY, 2, 10, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        Booking b1 = new Booking();
        b1.setStart(start.getTime());
        b1.setEnd(end.getTime());
        bookings.add(b1);
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);
        b2.setStart(start.getTime());
        b2.setEnd(end.getTime());
        bookings.add(b2);

        assertTrue(Utilities.findOverlap(bookings));
    }

    @Test
    @DisplayName("Test findOverlap False")
    void testFindOverlapFalse() {
        List<Booking> bookings = new ArrayList<>();
        start.set(2020, Calendar.FEBRUARY, 2, 10, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        Booking b1 = new Booking();
        b1.setStart(start.getTime());
        b1.setEnd(end.getTime());
        bookings.add(b1);
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 12, 0);
        b2.setStart(start.getTime());
        b2.setEnd(end.getTime());
        bookings.add(b2);

        assertFalse(Utilities.findOverlap(bookings));
    }

}