package com.techlabs.app.controller;

import com.techlabs.app.dto.JWTAuthResponse;
import com.techlabs.app.dto.LoginDTO;
import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO) {
		logger.info("Received login request for user: {}", loginDTO.getUsername());

		String token = authService.login(loginDTO);

		logger.info("User {} logged in successfully, token generated.", loginDTO.getUsername());

		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@ModelAttribute RegisterDTO registerDTO, @RequestParam("role") String role,
			@RequestParam(value = "file1") MultipartFile file1, @RequestParam(value = "file2") MultipartFile file2) {
		logger.info("Received registration request for user: {}", registerDTO.getUsername());
		logger.debug("Role: {}", role);
		logger.debug("Files received: {} and {}", file1.getOriginalFilename(), file2.getOriginalFilename());

		String result = authService.register(registerDTO, role, file1, file2);

		logger.info("User {} registered successfully with role {}.", registerDTO.getUsername(), role);

		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
