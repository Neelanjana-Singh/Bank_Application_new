package com.techlabs.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

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

	public Transaction(Long transactionId, LocalDateTime transactionTimestamp, @NotNull double amount,
			Account senderAccountNumber, Account receiverAccountNumber) {
		super();
		this.transactionId = transactionId;
		this.transactionTimestamp = transactionTimestamp;
		this.amount = amount;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public Transaction() {

	}

}