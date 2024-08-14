package com.techlabs.app.service;

import com.techlabs.app.dto.BankResponseDTO;
import com.techlabs.app.util.PagedResponse;

public interface BankService {

    BankResponseDTO getBankById(Long bankId);

    BankResponseDTO addNewBank(BankResponseDTO bankDTO);

    BankResponseDTO updateBank(BankResponseDTO bankDTO, Long bankId);

    void deactivateBank(Long bankId);

    PagedResponse<BankResponseDTO> getAllBanks(int page, int size);
}
