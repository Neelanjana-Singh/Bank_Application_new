package com.techlabs.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.techlabs.app.dto.LoginDTO;
import com.techlabs.app.dto.RegisterDTO;

public interface AuthService {
	String login(LoginDTO loginDTO);

	String register(RegisterDTO registerDTO, String role, MultipartFile file1, MultipartFile file2);
}
