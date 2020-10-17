package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.model.Business;
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

    private List<Booking> setupFindAll(Booking booking, Booking.BookingStatus status, Booking.BookingStatus status2, Business business) {
        List<Booking> temp = new ArrayList<>();
        if (booking != null) {
            temp.add(booking);
            if (booking.getId() != null) {
                doReturn(Optional.of(booking)).when(repo).findById(booking.getId());

            }
            if (booking.getWorker() != null) {
                doReturn(temp).when(repo).findAllByWorker_Id(booking.getWorker().getId());
                if (status != null) {
                    doReturn(temp).when(repo).findAllByWorker_IdAndStatus(booking.getWorker().getId(), status);
                    if (status2 != null) doReturn(temp).when(repo).findAllByWorker_IdAndStatusOrWorker_IdAndStatus(
                            booking.getWorker().getId(), status, booking.getWorker().getId(), status2);
                }
            }
            if (booking.getUser() != null) {
                doReturn(temp).when(repo).findAllByUser_Id(booking.getUser().getId());
                if (status != null) {
                    doReturn(temp).when(repo).findAllByUser_IdAndStatus(booking.getUser().getId(), status);
                    if (status2 != null) doReturn(temp).when(repo).findAllByUser_IdAndStatusOrUser_IdAndStatus(
                            booking.getUser().getId(), status, booking.getUser().getId(), status2);
                }
            }
            if (business != null) {
                doReturn(temp).when(repo).findAllByWorker_Business_Id(business.getId());
                if (status != null) {
                    doReturn(temp).when(repo).findAllByWorker_Business_IdAndStatus(business.getId(), status);
                }
            }
        }
        doReturn(temp).when(repo).findAll();
        if (status != null) doReturn(temp).when(repo).findAllByStatus(status);
        return temp;
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        setupFindAll(null, null, null, null);
        assertEquals(0, service.findAll().size());
        setupFindAll(new Booking(), null, null, null);
        assertEquals(1, service.findAll().size());
    }

    @Test
    @DisplayName("Test findAllByStatus")
    void testFindALlByStatus() {
        setupFindAll(new Booking(), Booking.BookingStatus.CONFIRMED, null, null);
        assertEquals(1, service.findAll(Booking.BookingStatus.CONFIRMED).size());
        assertEquals(0, service.findAll(Booking.BookingStatus.PENDING).size());
    }

    private Booking setupNewBooking(Long userId, Long workerId, Long businessId, Booking.BookingStatus status1, Booking.BookingStatus status2) {
        Booking temp = new Booking();
        temp.setUser(new User(userId, "customer", "123Qwe!"));
        Worker w1 = new Worker(new User(workerId, "worker", "123Qwe!"));
        w1.setBusiness(new Business(businessId, "Test Business", "description"));
        temp.setWorker(w1);
        setupFindAll(temp, status1, status2, w1.getBusiness());
        return temp;
    }

    @Test
    @DisplayName("Test findAllByWorker Success")
    void testFindAllByWorker() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByWorker(2L);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByWorker Not Found")
    void testFindAllByWorkerNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByWorker(1L);
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByWorkerAndStatus Success")
    void testFindAllByWorkerAndStatus() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByWorker(2L, Booking.BookingStatus.CONFIRMED);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByWorkerAndStatus Not Found")
    void testFindAllByWorkerAndStatusNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings;
        bookings = service.findByWorker(1L, Booking.BookingStatus.CONFIRMED);  // invalid worker id
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
        bookings = service.findByWorker(2L, Booking.BookingStatus.PENDING);  // invalid booking status
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByWorkerAndStatusOrStatus Success")
    void testFindAllByWorkerAndStatusOrStatus() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.CANCELLED);
        Collection<Booking> bookings;
        bookings = service.findByWorker(2L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.CANCELLED);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByWorkerAndStatus Not Found")
    void testFindAllByWorkerAndStatusOrStatusNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.CANCELLED);
        Collection<Booking> bookings;
        bookings = service.findByWorker(1L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.PENDING);  // invalid worker id
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
        bookings = service.findByUser(2L, Booking.BookingStatus.COMPLETED, Booking.BookingStatus.CANCELLED);  // invalid booking status
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByUser Success")
    void testFindAllByUser() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByUser(1L);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByUser Not Found")
    void testFindAllByUserNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByUser(2L);
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }


    @Test
    @DisplayName("Test findAllByUserAndStatus Success")
    void testFindAllByUserAndStatus() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByUser(1L, Booking.BookingStatus.CONFIRMED);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByUserAndStatus Not Found")
    void testFindAllByUserAndStatusNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings;
        bookings = service.findByUser(2L, Booking.BookingStatus.CONFIRMED);  // invalid user id
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
        bookings = service.findByUser(1L, Booking.BookingStatus.PENDING);  // invalid booking status
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByUserAndStatusOrStatus Success")
    void testFindAllByUserAndStatusOrStatus() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.CANCELLED);
        Collection<Booking> bookings;
        bookings = service.findByUser(1L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.CANCELLED);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByUserAndStatus Not Found")
    void testFindAllByUserAndStatusOrStatusNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.CANCELLED);
        Collection<Booking> bookings;
        bookings = service.findByUser(2L, Booking.BookingStatus.CONFIRMED, Booking.BookingStatus.PENDING);  // invalid user id
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
        bookings = service.findByUser(1L, Booking.BookingStatus.COMPLETED, Booking.BookingStatus.CANCELLED);  // invalid booking status
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByBusiness Success")
    void testFindAllByBusiness() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByBusiness(3L);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByBusiness Not Found")
    void testFindAllByBusinessNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByBusiness(1L);
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }


    @Test
    @DisplayName("Test findAllByUserAndStatus Success")
    void testFindAllByBusinessAndStatus() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings = service.findByBusiness(3L, Booking.BookingStatus.CONFIRMED);
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    @DisplayName("Test findAllByUserAndStatus Not Found")
    void testFindAllByBusinessAndStatusNotFound() {
        Booking b1 = setupNewBooking(1L, 2L, 3L, Booking.BookingStatus.CONFIRMED, null);
        Collection<Booking> bookings;
        bookings = service.findByBusiness(1L, Booking.BookingStatus.CONFIRMED);  // invalid business id
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
        bookings = service.findByBusiness(3L, Booking.BookingStatus.PENDING);  // invalid booking status
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindByIdSuccess() {
        Booking temp = new Booking();
        temp.setId(1L);
        setupFindAll(temp, null, null, null);
        assertNotNull(service.findById(temp.getId()));
        assertEquals(temp, service.findById(temp.getId()));
    }

    @Test
    @DisplayName("Test findById Null")
    void testFindByIdNull() {
        Booking temp = new Booking();
        temp.setId(1L);
        setupFindAll(temp, null, null, null);
        assertNull(service.findById(null));
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        Booking temp = new Booking();
        temp.setId(1L);
        setupFindAll(temp, null, null, null);
        assertNull(service.findById(2L));
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