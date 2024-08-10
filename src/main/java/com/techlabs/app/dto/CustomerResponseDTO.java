package com.techlabs.app.dto;

import java.util.List;

public class CustomerResponseDTO {
	private Long customerId;
	private String firstName;
	private String lastName;
	private double totalBalance;
	private List<AccountResponseDTO> accounts;

	// No-args constructor
	public CustomerResponseDTO() {
	}

	// All-args constructor
	public CustomerResponseDTO(Long customerId, String firstName, String lastName, double totalBalance,
			List<AccountResponseDTO> accounts) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.totalBalance = totalBalance;
		this.accounts = accounts;
	}

	// Getters
	public Long getCustomerId() {
		return customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public List<AccountResponseDTO> getAccounts() {
		return accounts;
	}

	// Setters
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public void setAccounts(List<AccountResponseDTO> accounts) {
		this.accounts = accounts;
	}
}
