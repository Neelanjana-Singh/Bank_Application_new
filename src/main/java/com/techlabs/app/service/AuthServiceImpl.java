package com.techlabs.app.service;

import com.techlabs.app.dto.LoginDTO;
import com.techlabs.app.dto.RegisterDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Admin;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.AdminRelatedException;
import com.techlabs.app.exception.CustomerRelatedException;
import com.techlabs.app.repository.AdminRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public String login(LoginDTO loginDTO) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDTO registerDTO, String role) {
	    // Check if username already exists
	    if (userRepository.existsByUsername(registerDTO.getUsername()) && role.equals("ROLE_CUSTOMER")) {
	        throw new CustomerRelatedException(
	                "Customer with the Username : " + registerDTO.getUsername() + " already exists");
	    }

	    if (userRepository.existsByUsername(registerDTO.getUsername()) && role.equals("ROLE_ADMIN")) {
	        throw new AdminRelatedException(
	                "Admin with the Username : " + registerDTO.getUsername() + " already exists");
	    }

	    // Create and populate User entity
	    User user = new User();
	    user.setFirstName(registerDTO.getFirstName());
	    user.setLastName(registerDTO.getLastName());
	    user.setUsername(registerDTO.getUsername());
	    user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

	    // Validate email field
	    if (registerDTO.getEmail() == null || registerDTO.getEmail().trim().isEmpty()) {
	        throw new IllegalArgumentException("Email must not be blank");
	    }
	    user.setEmail(registerDTO.getEmail());

	    // Create and link Customer or Admin entity based on role
	    if (role.equals("ROLE_ADMIN")) {
	        Admin admin = new Admin();
	        admin.setFirstName(registerDTO.getFirstName());
	        admin.setLastName(registerDTO.getLastName());
	        admin.setUser(user);
	        user.setAdmin(admin);
	        adminRepository.save(admin);
	    } else {
	        Customer customer = new Customer();
	        customer.setFirstName(registerDTO.getFirstName());
	        customer.setLastName(registerDTO.getLastName());
	        customer.setUser(user);
	        List<Account> accounts = new ArrayList<>();
	        customer.setAccounts(accounts);
	        customer.setTotalBalance(0);
	        user.setCustomer(customer);
	        customerRepository.save(customer);
	    }

	    // Set roles and save user
	    Set<Role> roles = new HashSet<>();
	    Role newRole = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Role Not Found"));
	    roles.add(newRole);
	    user.setRoles(roles);
	    userRepository.save(user);

	    return "Registration successful for role : " + role.substring(5);
	}

}