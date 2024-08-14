package com.techlabs.app.controller;

import com.techlabs.app.dto.BankResponseDTO;
import com.techlabs.app.service.BankService;
import com.techlabs.app.util.PagedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private static final Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    private BankService bankService;

    @GetMapping("/{bankId}")
    public ResponseEntity<BankResponseDTO> getBankById(@PathVariable Long bankId) {
        logger.info("Received request to get bank details for bankId: {}", bankId);

        BankResponseDTO bankResponse = bankService.getBankById(bankId);

        logger.info("Successfully retrieved bank details for bankId: {}", bankId);
        return ResponseEntity.ok(bankResponse);
    }

    @PostMapping
    public ResponseEntity<BankResponseDTO> addNewBank(@Validated @RequestBody BankResponseDTO bankDTO) {
        logger.info("Received request to add a new bank: {}", bankDTO.getFullName());

        BankResponseDTO createdBank = bankService.addNewBank(bankDTO);

        logger.info("Successfully added new bank: {}", createdBank.getFullName());
        return ResponseEntity.ok(createdBank);
    }

    @PutMapping("/{bankId}")
    public ResponseEntity<BankResponseDTO> updateBank(@PathVariable Long bankId, @Validated @RequestBody BankResponseDTO bankDTO) {
        logger.info("Received request to update bank with bankId: {}", bankId);

        BankResponseDTO updatedBank = bankService.updateBank(bankDTO, bankId);

        logger.info("Successfully updated bank with bankId: {}", bankId);
        return ResponseEntity.ok(updatedBank);
    }

    @GetMapping("/all")
    public ResponseEntity<PagedResponse<BankResponseDTO>> getAllBanks(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        logger.info("Received request to get all banks.");

        PagedResponse<BankResponseDTO> pagedBanks = bankService.getAllBanks(page, size);

        logger.info("Successfully retrieved all banks, total count: {}", pagedBanks.getTotalElements());
        return ResponseEntity.ok(pagedBanks);
    }

    @DeleteMapping("/{bankId}")
    public ResponseEntity<Void> deactivateBankById(@PathVariable Long bankId) {
        logger.info("Received request to deactivate bank with bankId: {}", bankId);

        bankService.deactivateBank(bankId);

        logger.info("Successfully deactivated bank with bankId: {}", bankId);
        return ResponseEntity.noContent().build();
    }
}
