package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Booking;
import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Hours Services acts as an intermediary between the repo and controller classes
 */
@Service
public class HoursService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    HoursRepository hoursRepository;

    /**
     * Service to update or save an Hours entity
     * @param hours entity to save/update
     * @return null if there were validation errors
     */
    public Hours saveOrUpdateHours(Hours hours) {
        Hours.HoursPK hoursId = hours.getId();
        if (hoursId == null) return null;  // must have a valid id
        Worker worker = hoursId.getWorker();
        if (worker == null) return null;  // worker object must exist (at least need id)
        Long workerId = worker.getId();
        if (workerId == null) return null;  // must have an id value
        Optional<Worker> existingWorker = workerRepository.findById(workerId);
        if (!existingWorker.isPresent()) return null;  // worker must already exist in the system
        LocalTime start = hours.getStart();
        LocalTime end = hours.getEnd();
        if (!(start == null || end == null)) {
            if (start.compareTo(end) >= 0) return null;
        }
        return hoursRepository.save(hours);
    }

    /**
     * Service method to delete an Hours entity
     * @param hours entity to delete
     * @return true if successful, otherwise false
     */
    public boolean deleteHours(Hours hours) {
        if (hours == null || hours.getId() == null) return false;
        Optional<Hours> h1 = hoursRepository.findById(hours.getId());
        if (h1.isPresent()) {
            hoursRepository.deleteById(hours.getId());
            return !hoursRepository.findById(hours.getId()).isPresent();  // return true if hours have been removed
        }
        return false;
    }

    /**
     * Service to delete Hours based on Hours.HoursPK
     * @param workerId id of Worker
     * @param dayOfWeek DayOfWeek enum value
     * @return true if successful, otherwise false
     */
    public boolean deleteHours(Long workerId, DayOfWeek dayOfWeek) {
        Optional<Worker> worker = workerRepository.findById(workerId);
        if (worker.isPresent()) {
            Hours.HoursPK hoursPK = new Hours.HoursPK(worker.get(), dayOfWeek);
            Optional<Hours> hours = hoursRepository.findById(hoursPK);
            if (hours.isPresent()) {
                hoursRepository.delete(hours.get());
                return !hoursRepository.findById(hoursPK).isPresent();  // return true if hours have been removed
            }
        }
        return false;
    }

    /**
     * return an Hours record based on ID
     * @param hoursPK ID of Hours
     * @return Hours object, or null if not found/errors
     */
    public Hours findById(Hours.HoursPK hoursPK) {
        return (hoursPK != null) ? hoursRepository.findById(hoursPK).orElse(null) : null;
    }

    /**
     * Returns an HOurs record based on Id values
     * @param worker id of Worker
     * @param dayOfWeek DayOfWeek enum value
     * @return Hours object, or null if not found/errors
     */
    public Hours findById(Worker worker, DayOfWeek dayOfWeek) {
        if (worker == null || dayOfWeek == null) return null;
        return hoursRepository.findById(new Hours.HoursPK(worker, dayOfWeek)).orElse(null);
    }

    /**
     * Return a list of Hours for a Worker
     * @param worker Worker entity
     * @return List of Hours for that worker
     */
    public List<Hours> findByWorker(Worker worker) {
        if (worker == null) return null;
        return hoursRepository.findById_Worker(worker);
    }

    /**
     * Service method to find available hours for a worker.
     *
     * First, it finds the workers working hours for that day.
     *
     * Next, it creates a list of sorted Bookings for the provided date and converts these to Hours objects, and appends
     * the workers finishing time (i.e. endTime of Hours) to the end of the list.
     *
     * After this, it checks to find any gaps in the sorted list of Hours - i.e. if the end of the previous is less than
     * the start of the next Hours object. If found, it will create and append a new Hours object to a list of available
     * hours. These are returned once complete.
     *
     * @param worker: Worker to check
     * @param date: date to query
     * @return List of Hours representing the Workers available time slots on the given date
     */
    public List<Hours> findAvailableByWorker(Worker worker, LocalDate date) {
        Hours hours = findById(worker, date.getDayOfWeek());
        if (hours != null) {
            List<Booking> bookings = worker.getBookings().stream()
                    .filter(b -> b.getStatus() == Booking.BookingStatus.PENDING || b.getStatus() == Booking.BookingStatus.CONFIRMED)
                    .filter(b -> b.getStart().toLocalDate().compareTo(date) == 0)
                    .sorted(Comparator.comparing(Booking::getStart)).collect(Collectors.toList());
            List<Hours> availableHours = new ArrayList<>();
            if (bookings.size() > 0) {
                List <Hours> hoursList = new ArrayList<>();
                bookings.forEach(booking -> hoursList.add(convertBookingToHours(booking, worker)));
                LocalTime end = hours.getStart();  // end of previous should be the workers starting hours initially
                LocalTime start;
                hours.setStart(hours.getEnd());
                hoursList.add(hours);  // add end of work hours to list, with start as end time
                for (Hours temp : hoursList) {  // for each hours in list, check if previous end is before start
                    start = temp.getStart();
                    if (end.isBefore(temp.getStart())) {  // if so, then add a new available hours slot
                        Hours.HoursPK hoursPK = new Hours.HoursPK();
                        hoursPK.setDayOfWeek(date.getDayOfWeek());
                        Hours newHours = new Hours(hoursPK);
                        newHours.setStart(end);
                        newHours.setEnd(start);
                        availableHours.add(newHours);  // new available hours has end of previous to start of next
                    }
                    end = temp.getEnd();
                }
            } else {
                availableHours.add(hours);
            }
            return availableHours;
        }
        return null;
    }

    /**
     * Helper method to convert a Booking to Hours representation - used for creating list of available slots
     * @param booking Booking to convert
     * @param worker worker for HoursPK id
     * @return converted Hours entity
     */
    private Hours convertBookingToHours(Booking booking, Worker worker) {
        Hours hours = new Hours(new Hours.HoursPK(worker, booking.getStart().getDayOfWeek()));
        hours.setStart(booking.getStart().toLocalTime());
        hours.setEnd(booking.getEnd().toLocalTime());
        return hours;
    }
}
