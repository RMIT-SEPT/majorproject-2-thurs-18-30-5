package com.rmit.sept.assignment.initial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

/**
 * Hours Entity - used to record working day and time of Workers
 * utilises a composite primary key, consisting of Worker.ID (FK) and DayOfWeek
 */
@Entity(name = "Hours")
@Table(name = "hours")
public class Hours {
    @EmbeddedId
    private HoursPK id;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime start;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime end;

    public Hours() {

    }

    public Hours(DayOfWeek dayOfWeek, Worker worker) {
        this.id = new HoursPK(worker, dayOfWeek);
    }

    public Hours(HoursPK hoursPK) {
        this.id = hoursPK;
    }

    public HoursPK getId() {
        return id;
    }

    public void setId(HoursPK id) {
        this.id = id;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public boolean setStart(String start) {
        try {
            this.start = LocalTime.parse(start);
            return true;
        } catch (DateTimeParseException dtpe) {
            System.err.println(dtpe.getMessage());
            return false;
        }
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public boolean setEnd(String end) {
        try {
            this.end = LocalTime.parse(end);
            return true;
        } catch (DateTimeParseException dtpe) {
            System.err.println(dtpe.getMessage());
            return false;
        }
    }

    /**
     * HoursPK creates the composite primary key for Hours entities - Worker.ID and DayOfWeek
     */
    @SuppressWarnings("JpaDataSourceORMInspection")  // errors with mapping to columns in table
    @Embeddable
    public static class HoursPK implements Serializable {
        @ManyToOne
        @JoinColumn(name = "worker_id")
        @JsonIgnoreProperties({"user", "business", "bookings", "createdAt", "updatedAt", "admin"})
        private Worker worker;
        @Column(name = "day_of_week")
        private DayOfWeek dayOfWeek;

        public HoursPK() {

        }

        public HoursPK(Worker worker, DayOfWeek dayOfWeek) {
            this.worker = worker;
            this.dayOfWeek = dayOfWeek;
        }

        public Worker getWorker() {
            return worker;
        }

        public void setWorker(Worker worker) {
            this.worker = worker;
        }

        public DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HoursPK hoursPK = (HoursPK) o;
            return getDayOfWeek().equals(hoursPK.getDayOfWeek()) &&
                    Objects.equals(getWorker(), hoursPK.getWorker());
        }

        @Override
        public int hashCode() {
            return Objects.hash(worker, dayOfWeek);
        }

        @Override
        public String toString() {
            return "HoursPK{" +
                    "worker=" + worker +
                    ", dayOfWeek=" + dayOfWeek +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hours hours = (Hours) o;
        return Objects.equals(id, hours.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Hours{" +
                "id=" + id.toString() +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
