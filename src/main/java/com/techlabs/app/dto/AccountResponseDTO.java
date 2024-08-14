package com.techlabs.app.dto;

public class AccountResponseDTO {
	private Long accountNumber;
	private double balance;
	private Long customerId;
	private Long bankId;
	private boolean isActive; // Newly added variable

	// No-args constructor
	public AccountResponseDTO() {
	}

	// All-args constructor
	public AccountResponseDTO(Long accountNumber, double balance, Long customerId, Long bankId, boolean isActive) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customerId = customerId;
		this.bankId = bankId;
		this.isActive = isActive;
	}

	// Constructor with partial fields
	public AccountResponseDTO(Long accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	// Getters
	public Long getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public Long getBankId() {
		return bankId;
	}

	public boolean isActive() {
		return isActive;
	}

	// Setters
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}