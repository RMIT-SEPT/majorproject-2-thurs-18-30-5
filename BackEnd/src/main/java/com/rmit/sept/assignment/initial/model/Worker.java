package com.rmit.sept.assignment.initial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Worker {
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @MapsId
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
}
