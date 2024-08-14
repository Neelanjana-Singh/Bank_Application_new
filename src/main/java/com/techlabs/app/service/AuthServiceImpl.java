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
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	private FileService fileService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public String login(LoginDTO loginDTO) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return jwtTokenProvider.generateToken(authentication);
		} catch (Exception e) {
			throw new RuntimeException("Authentication failed", e);
		}
	}

	@Override
	public String register(RegisterDTO registerDTO, String role, MultipartFile file1, MultipartFile file2) {
	    // Validate input
	    if (registerDTO.getEmail() == null || registerDTO.getEmail().trim().isEmpty()) {
	        throw new IllegalArgumentException("Email must not be blank");
	    }

	    // Check if username already exists
	    if (userRepository.existsByUsername(registerDTO.getUsername())) {
	        if (role.equals("ROLE_CUSTOMER")) {
	            throw new CustomerRelatedException(
	                    "Customer with the Username : " + registerDTO.getUsername() + " already exists");
	        } else if (role.equals("ROLE_ADMIN")) {
	            throw new AdminRelatedException(
	                    "Admin with the Username : " + registerDTO.getUsername() + " already exists");
	        }
	    }

	    User user = new User();
	    user.setFirstName(registerDTO.getFirstName());
	    user.setLastName(registerDTO.getLastName());
	    user.setUsername(registerDTO.getUsername());
	    user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
	    user.setEmail(registerDTO.getEmail());
	    
	    Admin admin = new Admin();
	    if (role.equals("ROLE_ADMIN")) {
	        admin.setFirstName(user.getFirstName());
	        admin.setLastName(user.getLastName());
	        admin.setUser(user);
	        user.setAdmin(admin);
	    }
	    
	    Customer customer = new Customer();
	    if (role.equals("ROLE_CUSTOMER")) {
	        customer.setFirstName(user.getFirstName());
	        customer.setLastName(user.getLastName());
	        customer.setUser(user);
	        customer.setTotalBalance(0);
	        customer.setActive(true); 
	        customer.setAccounts(new ArrayList<>());
	        user.setCustomer(customer);
	    }

	    // Set roles and save user
	    Set<Role> roles = new HashSet<>();
	    Role newRole = roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Role Not Found"));
	    roles.add(newRole);
	    user.setRoles(roles);
	    User savedUser = userRepository.save(user);
	    
	    if (role.equals("ROLE_ADMIN")) {
	        adminRepository.save(admin);
	    }

	    if (role.equals("ROLE_CUSTOMER")) {
	        customerRepository.save(customer);
	    }

	    fileService.uploadFile(file1, savedUser.getUserId());
	    fileService.uploadFile(file2, savedUser.getUserId());

	    // Send email notification after successful registration
	    if (role.equals("ROLE_CUSTOMER")) {
	        String text = "Creation of account";
	        String message = "Account created successfully";
	        emailService.sendMail(customer.getUser().getEmail(), text, message);
	    }

	    return "Registration successful for role : " + role.substring(5);
	}

}
