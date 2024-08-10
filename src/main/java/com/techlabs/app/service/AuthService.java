package com.techlabs.app.service;

import com.techlabs.app.dto.LoginDTO;
import com.techlabs.app.dto.RegisterDTO;

public interface AuthService {
	String login(LoginDTO loginDTO);

	String register(RegisterDTO registerDTO, String role);
}
