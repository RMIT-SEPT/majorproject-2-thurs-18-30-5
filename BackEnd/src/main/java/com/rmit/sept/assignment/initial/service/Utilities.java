package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;

import java.util.Comparator;
import java.util.List;

public class Utilities {
    static boolean findOverlap(List<Booking> bookings) {
        if (bookings.size() == 1) return false;
        bookings.sort(Comparator.comparing(Booking::getStart));
        for (int i = 1; i < bookings.size(); i++) {
            if ((bookings.get(i).getStart().compareTo(bookings.get(i - 1).getEnd()) <= 0) &&
                    (bookings.get(i).getEnd().compareTo(bookings.get(i - 1).getStart()) >= 0)) {
                System.err.println("OVERLAP");
                System.err.println(bookings.get(i - 1).getStart());
                System.err.println(bookings.get(i).getStart());
                System.err.println(bookings.get(i - 1).getEnd());
                System.err.println(bookings.get(i).getEnd());
                return true;
            }
        }
        return false;
    }
}
