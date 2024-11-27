package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(updatable = false)
    private LocalDateTime createdDtime;

    private LocalDateTime modifiedDtime;

    @PrePersist
    protected void onCreate() {
        createdDtime = LocalDateTime.now();
        modifiedDtime = createdDtime;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDtime = LocalDateTime.now();
    }

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDtime() {
        return createdDtime;
    }

    public LocalDateTime getModifiedDtime() {
        return modifiedDtime;
    }

    public void setModifiedDtime(LocalDateTime modifiedDtime) {
        this.modifiedDtime = modifiedDtime;
    }
}
