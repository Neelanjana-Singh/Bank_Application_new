package com.techlabs.app.dto;

public class AccountResponseDTO {
	private Long accountNumber;
	private double balance;
	private Long customerId;
	private Long bankId;

	// No-args constructor
	public AccountResponseDTO() {
	}

	// All-args constructor
	public AccountResponseDTO(Long accountNumber, double balance, Long customerId, Long bankId) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customerId = customerId;
		this.bankId = bankId;
	}

	

	public AccountResponseDTO(Long accountNumber, double balance) {
		super();
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
}
