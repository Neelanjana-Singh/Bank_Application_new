package com.techlabs.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankId;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String fullName;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String abbreviation;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    // Getters and Setters

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    // Constructor with isActive parameter
    public Bank(Long bankId, @NotBlank String fullName, @NotBlank String abbreviation, boolean isActive, List<Account> accounts) {
        this.bankId = bankId;
        this.fullName = fullName;
        this.abbreviation = abbreviation;
        this.isActive = isActive;
        this.accounts = accounts;
    }

    // Default Constructor
    public Bank() {
    }
}