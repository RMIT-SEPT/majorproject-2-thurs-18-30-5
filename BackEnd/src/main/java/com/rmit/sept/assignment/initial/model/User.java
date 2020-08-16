package com.rmit.sept.assignment.initial.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 6, max = 15, message = "Username must be between 6 and 15 characters")
    @NotBlank(message = "Username may not be blank")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Password may not be blank")
    private String password;
    private String firstName;
    private String lastName;
    private String address;
//    TODO: phone number (area code + number?), separate address into fields?
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date updatedAt;

    public User() {

    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.setUsername(username);
        this.setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new java.util.Date();
        this.updatedAt = this.createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new java.util.Date();
    }
}
