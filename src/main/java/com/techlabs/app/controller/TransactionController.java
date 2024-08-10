package com.techlabs.app.controller;

import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createNewTransaction(@RequestBody TransactionResponseDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.createNewTransaction(transactionDTO));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long transactionId,
                                                                    @RequestBody TransactionResponseDTO transactionDTO) {
        return ResponseEntity.ok(transactionService.updateTransaction(transactionDTO, transactionId));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
