package com.rmit.sept.assignment.initial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Worker entities are used to track users who are workers. Additionally, a worker may also be an admin user.
 */
@Entity(name = "Worker")
@Table(name = "worker")
public class Worker {
    @Id
    @NotNull(message = "Worker ID must be provided")
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @MapsId
    @PrimaryKeyJoinColumn
    private User user;

    private Boolean isAdmin = false;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date updatedAt;

    public Worker() {

    }

    public Worker(User user) {
        this();
        setUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user != null) {
            this.id = user.getId();
            this.user = user;
        }
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new java.util.Date();
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new java.util.Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker that = (Worker) o;
        return getId().equals(that.getId());
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", user=" + user +
                ", isAdmin=" + isAdmin +
//                ", hours=" + hours +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
