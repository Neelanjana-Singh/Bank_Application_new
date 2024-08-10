package com.techlabs.app.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterDTO {
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String email;

	@NotBlank
	private String phoneNumber;

	@NotBlank
	private String accountNumber;

	// Getters
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	// Setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	// toString method
	@Override
	public String toString() {
		return "RegisterDTO{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", username='"
				+ username + '\'' + ", password='" + password + '\'' + ", email='" + email + '\'' + ", phoneNumber='"
				+ phoneNumber + '\'' + ", accountNumber='" + accountNumber + '\'' + '}';
	}
}
