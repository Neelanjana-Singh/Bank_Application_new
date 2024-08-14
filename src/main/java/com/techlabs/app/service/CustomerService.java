package com.techlabs.app.service;

import com.techlabs.app.util.PagedResponse;
import com.techlabs.app.dto.CustomerResponseDTO;

public interface CustomerService {

	CustomerResponseDTO getCustomerById(Long customerId);

	CustomerResponseDTO depositToAccount(Long customerId, Long accountId, double amount);

	CustomerResponseDTO withdrawFromAccount(Long customerId, Long accountId, double amount);

	double getTotalBalance(Long customerId);

	void deactivate(Long customerId);

	PagedResponse<CustomerResponseDTO> getAllCustomers(int page, int size);
}
