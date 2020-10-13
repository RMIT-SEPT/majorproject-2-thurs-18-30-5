package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.repositories.BookingRepository;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

@SpringBootTest
@ActiveProfiles("test")
class BookingServiceTest {
    @Autowired
    private BookingService service;
    @MockBean
    private BookingRepository repo;
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

    @Test
    @DisplayName("Test saveOrUpdate Create Success")
    void testCreateBooking() {
        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);


        Booking b1 = new Booking(1L);
        b1.setStart(toLocalDateTime(start));
        b1.setEnd(toLocalDateTime(end));

        b1.setWorker(w1);
        b1.setUser(u1);

        doReturn(Optional.of(w1)).when(workerRepo).findById(1L);
        doReturn(Optional.empty()).when(workerRepo).findById(2L);
        doReturn(Optional.of(u1)).when(userRepo).findById(2L);
        doReturn(b1).when(repo).save(any());
        doReturn(new ArrayList<>()).when(repo).findAllByUser_IdAndStatus(any(), any());  // empty list => no overlap
        doReturn(new ArrayList<>()).when(repo).findAllByWorker_IdAndStatus(any(), any());  // empty list => no overlap

        Booking b2 = service.saveOrUpdateBooking(b1, true);

        assertNotNull(b2);
        assertEquals(b1.getId(), b2.getId());
    }

    @Test
    @DisplayName("Test saveOrUpdate Update Success")
    void testUpdateBooking() {
        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);

        Booking b1 = new Booking(1L);
        b1.setStart(toLocalDateTime(start));
        b1.setEnd(toLocalDateTime(end));

        b1.setWorker(w1);
        b1.setUser(u1);

        doReturn(Optional.of(w1)).when(workerRepo).findById(1L);
        doReturn(Optional.empty()).when(workerRepo).findById(2L);
        doReturn(Optional.of(u1)).when(userRepo).findById(2L);
        doReturn(b1).when(repo).save(any());
        doReturn(new ArrayList<>()).when(repo).findAllByUser_IdAndStatus(any(), any());  // empty list => no overlap
        doReturn(new ArrayList<>()).when(repo).findAllByWorker_IdAndStatus(any(), any());  // empty list => no overlap

        Booking b2 = service.saveOrUpdateBooking(b1, true);

        assertNotNull(b2);
        assertEquals(b1.getId(), b2.getId());

        start.set(2020, Calendar.FEBRUARY, 2, 9, 0);
        b2.setStart(toLocalDateTime(start));
        doReturn(b2).when(repo).save(any());

        Booking b3 = service.saveOrUpdateBooking(b2, false);
        assertNotNull(b3);
        assertEquals(b2.getId(), b3.getId());
        assertEquals(b2.getStart(), b3.getStart());
    }

//
//    @Test
//    @DisplayName("Test create Failure (overlap)")
//    void testCreateBookingOverlap() {
//        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
//        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);
//        User u2 = new User(3L, "anotheruser", "123Qwe!");
//        Booking b1 = new Booking(1L);
//        b1.setStart(toLocalDateTime(start));
//        b1.setEnd(toLocalDateTime(end));
//        b1.setWorker(w1);
//        b1.setUser(u2);
//        b1.setStatus(Booking.BookingStatus.PENDING);
//
//        doReturn(Optional.of(w1)).when(workerRepo).findById(1L);
//        doReturn(Optional.empty()).when(workerRepo).findById(2L);
//        doReturn(Optional.of(u1)).when(userRepo).findById(2L);
//        doReturn(Optional.of(u2)).when(userRepo).findById(3L);
//        doReturn(b1).when(repo).save(any());
//
//        List<Booking> workerBookigns = new ArrayList<>();
//        workerBookigns.add(b1);
//
//        doReturn(new ArrayList<>()).when(repo).findAllByUser_IdAndStatus(u1.getId(), Booking.BookingStatus.PENDING);  // empty list => no overlap
//        doReturn(workerBookigns).when(repo).findAllByWorker_IdAndStatus(w1.getId(), Booking.BookingStatus.PENDING);  // NON-EMPTY list (with overlap) overlap
//
//        b1.setUser(u1);
//        Booking b2 = service.saveOrUpdateBooking(b1, true);
//
//        assertNull(b2);
//    }

    @Test
    @DisplayName("Test create Success (overlap but not pending)")
    void testCreateBookingOverlapSuccess() {
        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);
        User u2 = new User(3L, "anotheruser", "123Qwe!");
        Booking b1 = new Booking(1L);
        b1.setStart(toLocalDateTime(start));
        b1.setEnd(toLocalDateTime(end));
        b1.setWorker(w1);
        b1.setUser(u2);

        doReturn(Optional.of(w1)).when(workerRepo).findById(1L);
        doReturn(Optional.empty()).when(workerRepo).findById(2L);
        doReturn(Optional.of(u1)).when(userRepo).findById(2L);
        doReturn(Optional.of(u2)).when(userRepo).findById(3L);
        doReturn(b1).when(repo).save(any());

        doReturn(new ArrayList<>()).when(repo).findAllByUser_IdAndStatus(any(), any());  // empty list => no overlap
        doReturn(new ArrayList<>()).when(repo).findAllByWorker_IdAndStatus(any(), any());  // NON-EMPTY list (with overlap) overlap

        b1.setUser(u1);
        Booking b2 = service.saveOrUpdateBooking(b1, true);

        assertNotNull(b2);
    }

    /**
     * Helper method to update testing - Booking uses LocalDateTime, but tests were created when Booking class used Date
     * Source: https://www.logicbig.com/how-to/java-8-date-time-api/calender-to-localdatetime.html
     * @param calendar: calendar to update
     * @return LocalDateTime equivalent of calendar
     */
    private static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }
}