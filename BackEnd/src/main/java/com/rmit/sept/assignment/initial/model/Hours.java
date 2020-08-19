package com.rmit.sept.assignment.initial.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Hours")
@Table(name = "hours")
public class Hours {
    @EmbeddedId
    private HoursPK id;
    private Date start;
    private Date end;

    public Hours() {
    }

    public HoursPK getId() {
        return id;
    }

    public void setId(HoursPK id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Embeddable
    public static class HoursPK implements Serializable {
        @ManyToOne
        @JoinColumn(name = "worker_id")
        private Worker worker;
        @Column(name = "day_of_week")
        private Long dayOfWeek;

        public HoursPK() {

        }

        public HoursPK(Worker worker, Long dayOfWeek) {
            this.worker = worker;
            this.dayOfWeek = dayOfWeek;
        }

        public Worker getWorker() {
            return worker;
        }

        public Long getDayOfWeek() {
            return dayOfWeek;
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
                    "worker=" + worker.toString() +
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
