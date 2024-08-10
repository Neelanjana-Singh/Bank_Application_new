package com.techlabs.app.controller;

import com.techlabs.app.dto.JWTAuthResponse;
import com.techlabs.app.dto.LoginDTO;
import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO) {
		String token = authService.login(loginDTO);
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO, @RequestParam("role") String role) {
		String result = authService.register(registerDTO, role);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	
}
