package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.util.PagedResponse;

public interface AccountService {

	AccountResponseDTO save(AccountResponseDTO accountDTO);

	// void delete(Long accountNumber);
	void deactivate(Long accountNumber); 

	List<AccountResponseDTO> findByCustomerId(Long customerId);

	AccountResponseDTO update(AccountResponseDTO accountDTO, long accountNumber);

	PagedResponse<AccountResponseDTO> findByCustomerId(Long customerId, int page, int size, String sortBy,
			String direction);

	
}