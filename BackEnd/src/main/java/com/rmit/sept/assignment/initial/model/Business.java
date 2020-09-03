package com.rmit.sept.assignment.initial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity(name = "Business")
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(min = 3, max = 40, message = "Service name must be between 3 and 40 characters")
    @NotBlank(message = "Service name cannot be blank")
    private String name;
    private String description;

    @OneToMany(targetEntity=Worker.class, mappedBy="business", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("business")
    private List<Worker> workers;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private Date updatedAt;

    public Business() {}

    public Business(String name) {
        this.name = name;
    }

    public Business(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
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
        Business business = (Business) o;
        return id.equals(business.id);
    }

    @Override
    public String toString() {
        return "Business{" +
                "\n\tid=" + id +
                ",\n\tname='" + name + '\'' +
                ",\n\tdescription='" + description + '\'' +
                ",\n\tnumber of works=" + workers.size() +
                '}';
    }
}
