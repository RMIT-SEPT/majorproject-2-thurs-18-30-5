package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    @DisplayName("Test findOverlap True Duplicate")
    void testFindOverlapFalseDuplicate() {
        List<Booking> bookings = new ArrayList<>();
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 11, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 12, 0);
        b2.setStart(start.getTime());
        b2.setEnd(end.getTime());
        Booking b1 = new Booking();
        b1.setStart(start.getTime());
        b1.setEnd(end.getTime());
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
        b1.setStart(start.getTime());
        b1.setEnd(end.getTime());
        bookings.add(b1);
        Booking b2 = new Booking();
        start.set(2020, Calendar.FEBRUARY, 2, 11, 31);
        end.set(2020, Calendar.FEBRUARY, 2, 12, 0);
        b2.setStart(start.getTime());
        b2.setEnd(end.getTime());
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
        b2.setStart(start.getTime());
        b2.setEnd(end.getTime());
        start.set(2020, Calendar.FEBRUARY, 2, 10, 30);
        end.set(2020, Calendar.FEBRUARY, 2, 11, 29);
        Booking b1 = new Booking();
        b1.setStart(start.getTime());
        b1.setEnd(end.getTime());
        bookings.add(b1);
        bookings.add(b2);

        assertFalse(Utilities.findOverlap(bookings));
    }
}