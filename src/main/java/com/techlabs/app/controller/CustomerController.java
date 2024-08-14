package com.techlabs.app.controller;

import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.service.CustomerService;
import com.techlabs.app.util.PagedResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@GetMapping("/{customerId}/balance")
	public ResponseEntity<Double> getTotalBalance(@PathVariable Long customerId) {
		logger.info("Fetching total balance for customerId: {}", customerId);
		return ResponseEntity.ok(customerService.getTotalBalance(customerId));
	}

	@PostMapping("/{customerId}/deposit")
	public ResponseEntity<CustomerResponseDTO> depositToAccount(@PathVariable Long customerId,
			@RequestParam Long accountId, @RequestParam double amount) {
		logger.info("Depositing amount: {} to accountId: {} for customerId: {}", amount, accountId, customerId);
		return ResponseEntity.ok(customerService.depositToAccount(customerId, accountId, amount));
	}

	@PostMapping("/{customerId}/withdraw")
	public ResponseEntity<CustomerResponseDTO> withdrawFromAccount(@PathVariable Long customerId,
			@RequestParam Long accountId, @RequestParam double amount) {
		logger.info("Withdrawing amount: {} from accountId: {} for customerId: {}", amount, accountId, customerId);
		return ResponseEntity.ok(customerService.withdrawFromAccount(customerId, accountId, amount));
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerResponseDTO> getCustomerDetails(@PathVariable Long customerId) {
		logger.info("Fetching customer details for customerId: {}", customerId);
		return ResponseEntity.ok(customerService.getCustomerById(customerId));
	}

	

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> deactivateCustomer(@PathVariable Long customerId) {
		logger.info("Deactivating customer with customerId: {}", customerId);
		customerService.deactivate(customerId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<PagedResponse<CustomerResponseDTO>> getAllCustomers(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		logger.info("Fetching all customers with pagination - page: {}, size: {}", page, size);
		PagedResponse<CustomerResponseDTO> pagedCustomers = customerService.getAllCustomers(page, size);
		return ResponseEntity.ok(pagedCustomers);
	}
}
