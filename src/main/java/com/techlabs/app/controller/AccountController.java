package com.techlabs.app.controller;

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.service.AccountService;
import com.techlabs.app.util.PagedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountResponseDTO accountResponseDTO) {
		logger.info("Received request to create account with details: {}", accountResponseDTO);
		try {
			AccountResponseDTO savedAccount = accountService.save(accountResponseDTO);
			logger.info("Account created successfully with ID: {}", savedAccount.getAccountNumber());
			return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
		} catch (Exception e) {
			logger.error("Error creating account: {}", e.getMessage(), e);
			throw e;
		}
	}

	@DeleteMapping("/{accountNumber}")
	public ResponseEntity<Void> deactivateAccount(@PathVariable Long accountNumber) {
		logger.info("Received request to deactivate account with ID: {}", accountNumber);
		try {
			accountService.deactivate(accountNumber);
			logger.info("Account with ID: {} deactivated successfully", accountNumber);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			logger.error("Error deactivating account with ID: {}: {}", accountNumber, e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<PagedResponse<AccountResponseDTO>> getAccountsByCustomerId(@PathVariable Long customerId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sort", defaultValue = "accountNumber") String sortBy,
			@RequestParam(name = "direction", defaultValue = "asc") String direction) {
		logger.info("Received request to get accounts for customer with ID: {}", customerId);
		try {
			PagedResponse<AccountResponseDTO> pagedAccounts = accountService.findByCustomerId(customerId, page, size,
					sortBy, direction);
			logger.info("Found {} accounts for customer with ID: {}", pagedAccounts.getContent().size(), customerId);
			return ResponseEntity.ok(pagedAccounts);
		} catch (Exception e) {
			logger.error("Error retrieving accounts for customer with ID: {}: {}", customerId, e.getMessage(), e);
			throw e;
		}
	}

	@PutMapping("/{accountNumber}")
	public ResponseEntity<AccountResponseDTO> updateAccount(@RequestBody AccountResponseDTO accountDTO,
			@PathVariable long accountNumber) {
		logger.info("Received request to update account with ID: {} and details: {}", accountNumber, accountDTO);
		try {
			AccountResponseDTO updatedAccount = accountService.update(accountDTO, accountNumber);
			logger.info("Account with ID: {} updated successfully", accountNumber);
			return ResponseEntity.ok(updatedAccount);
		} catch (Exception e) {
			logger.error("Error updating account with ID: {}: {}", accountNumber, e.getMessage(), e);
			throw e; 
		}
	}
}
