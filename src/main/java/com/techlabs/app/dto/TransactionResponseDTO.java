package com.techlabs.app.dto;

import java.time.LocalDateTime;

public class TransactionResponseDTO {
	private Long transactionId;
	private LocalDateTime transactionTimestamp;
	private double amount;
	private Long senderAccountNumber;
	private Long receiverAccountNumber;

	// No-args constructor
	public TransactionResponseDTO() {
	}

	// All-args constructor
	public TransactionResponseDTO(Long transactionId, LocalDateTime transactionTimestamp, double amount,
			Long senderAccountNumber, Long receiverAccountNumber) {
		this.transactionId = transactionId;
		this.transactionTimestamp = transactionTimestamp;
		this.amount = amount;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
	}

	// Getters
	public Long getTransactionId() {
		return transactionId;
	}

	public LocalDateTime getTransactionTimestamp() {
		return transactionTimestamp;
	}

	public double getAmount() {
		return amount;
	}

	public Long getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public Long getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	// Setters
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public void setTransactionTimestamp(LocalDateTime transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setSenderAccountNumber(Long senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public void setReceiverAccountNumber(Long receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}
}
