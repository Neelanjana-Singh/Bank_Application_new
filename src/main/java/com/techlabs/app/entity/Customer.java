package com.techlabs.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private Long customerId;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private double totalBalance;

    @Column(nullable = false)
    private boolean isActive;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customerId")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    // Getters and Setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    // Constructor with isActive parameter
    public Customer(Long customerId, @NotBlank String firstName, @NotBlank String lastName, double totalBalance, boolean isActive, User user, List<Account> accounts) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalBalance = totalBalance;
        this.isActive = isActive;
        this.user = user;
        this.accounts = accounts;
    }

    // Default Constructor
    public Customer() {
    }
}