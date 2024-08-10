package com.techlabs.app.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	// No-args constructor
	public LoginDTO() {
	}

	// All-args constructor
	public LoginDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Getters
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	// Setters
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
