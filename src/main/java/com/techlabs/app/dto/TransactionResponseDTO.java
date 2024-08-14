package com.techlabs.app.dto;

import java.time.LocalDateTime;

public class TransactionResponseDTO {
    private Long transactionId;
    private LocalDateTime transactionTimestamp;
    private double amount;
    private Long senderAccountNumber;
    private Long receiverAccountNumber;
    private boolean isActive; // New field

    // No-args constructor
    public TransactionResponseDTO() {
    }

    // All-args constructor
    public TransactionResponseDTO(Long transactionId, LocalDateTime transactionTimestamp, double amount,
            Long senderAccountNumber, Long receiverAccountNumber, boolean isActive) {
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

    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(Long senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public Long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(Long receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
