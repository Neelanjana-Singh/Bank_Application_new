package com.techlabs.app.controller;

import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{customerId}/balance")
    public ResponseEntity<Double> getTotalBalance(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getTotalBalance(customerId));
    }

    @PostMapping("/{customerId}/deposit")
    public ResponseEntity<CustomerResponseDTO> depositToAccount(@PathVariable Long customerId,
                                                                @RequestParam Long accountId,
                                                                @RequestParam double amount) {
        return ResponseEntity.ok(customerService.depositToAccount(customerId, accountId, amount));
    }

    @PostMapping("/{customerId}/withdraw")
    public ResponseEntity<CustomerResponseDTO> withdrawFromAccount(@PathVariable Long customerId,
                                                                   @RequestParam Long accountId,
                                                                   @RequestParam double amount) {
        return ResponseEntity.ok(customerService.withdrawFromAccount(customerId, accountId, amount));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerDetails(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }
}
