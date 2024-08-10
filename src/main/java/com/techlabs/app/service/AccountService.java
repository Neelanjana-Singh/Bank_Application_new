package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.AccountResponseDTO;

public interface AccountService {

	AccountResponseDTO save(AccountResponseDTO accountDTO);

	void delete(Long accountNumber);

	List<AccountResponseDTO> findByCustomerId(Long customerId);

	AccountResponseDTO update(AccountResponseDTO accountDTO, long accountNumber);
}