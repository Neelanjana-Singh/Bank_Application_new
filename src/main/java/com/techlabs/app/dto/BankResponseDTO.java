package com.techlabs.app.dto;

public class BankResponseDTO {
	private Long bankId;
	private String fullName;
	private String abbreviation;
	private boolean isActive; // Newly added variable

	// No-args constructor
	public BankResponseDTO() {
	}

	// All-args constructor
	public BankResponseDTO(Long bankId, String fullName, String abbreviation, boolean isActive) {
		this.bankId = bankId;
		this.fullName = fullName;
		this.abbreviation = abbreviation;
		this.isActive = isActive;
	}

	// Getters
	public Long getBankId() {
		return bankId;
	}

	public String getFullName() {
		return fullName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public boolean isActive() {
		return isActive;
	}

	// Setters
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}