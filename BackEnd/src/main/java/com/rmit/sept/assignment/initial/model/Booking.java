package com.rmit.sept.assignment.initial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Booking")
@Table(name = "booking")
public class Booking {
    public enum BookingStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "worker_id")
    @JsonIgnoreProperties("bookings")
    private Worker worker;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("bookings")
    private User user;

    @NotNull(message = "Start date cannot be null")
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date start;
    @NotNull(message = "End date cannot be null")
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date end;

    private BookingStatus status;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date updatedAt;

    public Booking() {

    }

    public Long getId() {
        return id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }
}
