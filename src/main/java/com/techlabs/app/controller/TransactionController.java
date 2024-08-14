package com.techlabs.app.controller;

import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.service.TransactionService;
import com.techlabs.app.util.PagedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/{transactionId}")
	public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long transactionId) {
		logger.info("Received request to get transaction by ID: {}", transactionId);
		try {
			TransactionResponseDTO response = transactionService.getTransactionById(transactionId);
			logger.info("Successfully retrieved transaction: {}", response);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error retrieving transaction by ID: {}", transactionId, e);
			throw e;
		}
	}

	@PostMapping
	public ResponseEntity<TransactionResponseDTO> createNewTransaction(
			@RequestBody TransactionResponseDTO transactionDTO) {
		logger.info("Received request to create a new transaction: {}", transactionDTO);
		try {
			TransactionResponseDTO response = transactionService.createNewTransaction(transactionDTO);
			logger.info("Successfully created transaction: {}", response);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error creating new transaction: {}", transactionDTO, e);
			throw e;
		}
	}

	@PutMapping("/{transactionId}")
	public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long transactionId,
			@RequestBody TransactionResponseDTO transactionDTO) {
		logger.info("Received request to update transaction ID {} with details: {}", transactionId, transactionDTO);
		try {
			TransactionResponseDTO response = transactionService.updateTransaction(transactionDTO, transactionId);
			logger.info("Successfully updated transaction: {}", response);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error updating transaction ID {} with details: {}", transactionId, transactionDTO, e);
			throw e;
		}
	}

	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Void> deactivateTransaction(@PathVariable Long transactionId) {
		logger.info("Received request to deactivate transaction ID: {}", transactionId);
		try {
			transactionService.deactivateTransaction(transactionId);
			logger.info("Successfully deactivated transaction ID: {}", transactionId);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			logger.error("Error deactivating transaction ID: {}", transactionId, e);
			throw e;
		}
	}

	@GetMapping("/all")
	public ResponseEntity<PagedResponse<TransactionResponseDTO>> getAllTransactions(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		logger.info("Received request to get all transactions with page: {} and size: {}", page, size);
		try {
			PagedResponse<TransactionResponseDTO> response = transactionService.getAllTransactions(page, size);
			logger.info("Successfully retrieved transactions");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error retrieving all transactions with page: {} and size: {}", page, size, e);
			throw e;
		}
	}

	@GetMapping("/date")
	public ResponseEntity<PagedResponse<TransactionResponseDTO>> getAllTransactionsByDateRange(
			@RequestParam(name = "startDate") LocalDateTime startDate,
			@RequestParam(name = "endDate") LocalDateTime endDate, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		logger.info("Received request to get transactions by date range: {} to {} with page: {} and size: {}",
				startDate, endDate, page, size);
		try {
			PagedResponse<TransactionResponseDTO> response = transactionService.getAllTransactionsByDateRange(startDate,
					endDate, page, size);
			logger.info("Successfully retrieved transactions for date range: {} to {}", startDate, endDate);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error retrieving transactions for date range: {} to {} with page: {} and size: {}", startDate,
					endDate, page, size, e);
			throw e;
		}
	}
}
