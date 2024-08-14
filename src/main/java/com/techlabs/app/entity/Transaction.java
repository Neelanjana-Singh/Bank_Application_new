package com.techlabs.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private LocalDateTime transactionTimestamp;

    @NotNull
    @Column(nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "sender_account")
    private Account senderAccountNumber;

    @ManyToOne
    @JoinColumn(name = "receiver_account")
    private Account receiverAccountNumber;

    @Column(nullable = false)
    private boolean isActive = true; // New field for soft delete

    // No-args constructor
    public Transaction() {
    }

    // All-args constructor
    public Transaction(Long transactionId, LocalDateTime transactionTimestamp, @NotNull double amount,
            Account senderAccountNumber, Account receiverAccountNumber, boolean isActive) {
        this.transactionId = transactionId;
        this.transactionTimestamp = transactionTimestamp;
        this.amount = amount;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.isActive = isActive;
    }

    // Getters and setters

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(LocalDateTime transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(Account senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public Account getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(Account receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
