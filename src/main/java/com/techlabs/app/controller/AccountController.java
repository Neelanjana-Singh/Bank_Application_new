package com.techlabs.app.controller;

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<AccountResponseDTO> saveAccount(@RequestBody AccountResponseDTO accountDTO) {
		return ResponseEntity.ok(accountService.save(accountDTO));
	}

	@PutMapping("/{accountNumber}")
	public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long accountNumber,
			@RequestBody AccountResponseDTO accountDTO) {
		return ResponseEntity.ok(accountService.update(accountDTO, accountNumber));
	}

	@DeleteMapping("/{accountNumber}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long accountNumber) {
		accountService.delete(accountNumber);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/by-customer/{customerId}")
	public ResponseEntity<List<AccountResponseDTO>> getAccountsByCustomerId(@PathVariable Long customerId) {
		return ResponseEntity.ok(accountService.findByCustomerId(customerId));
	}
}