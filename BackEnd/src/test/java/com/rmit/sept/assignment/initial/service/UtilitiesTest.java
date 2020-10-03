package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class UtilitiesTest {

    private final Calendar start = Calendar.getInstance();
    private final Calendar end = Calendar.getInstance();

    @Test
    @DisplayName("Test findOverlap True Overlap")
    void testFindOverlap() {
        List<Booking> bookings = new ArrayList<>();
        start.set(2020, Calendar.FEBRUARY, 2, 10, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        Booking b1 = new Booking();
        b1.setStart(toLocalDateTime(start));
        b1.setEnd(toLocalDateTime(end));
        bookings.add(b1);
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 10, 0);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 0);
        b2.setStart(toLocalDateTime(start));
        b2.setEnd(toLocalDateTime(end));
        bookings.add(b2);

        assertTrue(Utilities.findOverlap(bookings));
    }

    @Test
    @DisplayName("Test findOverlap True Duplicate")
    void testFindOverlapFalseDuplicate() {
        List<Booking> bookings = new ArrayList<>();
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 12, 0);
        b2.setStart(toLocalDateTime(start));
        b2.setEnd(toLocalDateTime(end));
        Booking b1 = new Booking();
        b1.setStart(toLocalDateTime(start));
        b1.setEnd(toLocalDateTime(end));
        bookings.add(b1);
        bookings.add(b2);

        assertTrue(Utilities.findOverlap(bookings));
    }

    @Test
    @DisplayName("Test findOverlap False No Overlap")
    void testFindOverlapFalse() {
        List<Booking> bookings = new ArrayList<>();
        start.set(2020, Calendar.FEBRUARY, 2, 10, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        Booking b1 = new Booking();
        b1.setStart(toLocalDateTime(start));
        b1.setEnd(toLocalDateTime(end));
        bookings.add(b1);
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 11, 31);
        end.set(2020, Calendar.FEBRUARY, 2, 12, 0);
        b2.setStart(toLocalDateTime(start));
        b2.setEnd(toLocalDateTime(end));
        bookings.add(b2);

        assertFalse(Utilities.findOverlap(bookings));
    }


    @Test
    @DisplayName("Test findOverlap False Sort No Overlap")
    void testFindOverlapFalseSort() {
        List<Booking> bookings = new ArrayList<>();
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 12, 0);
        b2.setStart(toLocalDateTime(start));
        b2.setEnd(toLocalDateTime(end));
        start.set(2020, Calendar.FEBRUARY, 2, 10, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 29);
        Booking b1 = new Booking();
        b1.setStart(toLocalDateTime(start));
        b1.setEnd(toLocalDateTime(end));
        bookings.add(b1);
        bookings.add(b2);

        assertFalse(Utilities.findOverlap(bookings));
    }

    /**
     * Simple method to update testing - moved to Booking entity LocalDateTime, while tests were created using Date
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