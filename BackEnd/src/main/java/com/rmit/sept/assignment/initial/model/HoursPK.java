package com.rmit.sept.assignment.initial.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class HoursPK implements Serializable {
    private int dayOfWeek;
    private Long workerId;

    public HoursPK() {

    }

    public HoursPK(Long workerId, int dayOfWeek) {
        this.workerId = workerId;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HoursPK hoursPK = (HoursPK) o;
        return dayOfWeek == hoursPK.dayOfWeek &&
                Objects.equals(workerId, hoursPK.workerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workerId, dayOfWeek);
    }

    @Override
    public String toString() {
        return "HoursPK{" +
                "workerId=" + workerId +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
}
