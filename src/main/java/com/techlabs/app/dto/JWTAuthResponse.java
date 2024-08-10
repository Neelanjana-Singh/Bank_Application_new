package com.techlabs.app.dto;

public class JWTAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	// No-args constructor
	public JWTAuthResponse() {
	}

	// All-args constructor
	public JWTAuthResponse(String accessToken, String tokenType) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	// Getters
	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	// Setters
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
