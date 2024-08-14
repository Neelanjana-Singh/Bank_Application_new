package com.techlabs.app.service;

import com.techlabs.app.dto.BankResponseDTO;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.exception.ResourceNotFoundException;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    @Override
    public BankResponseDTO getBankById(Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        return mapToDTO(bank);
    }

    @Override
    public BankResponseDTO addNewBank(BankResponseDTO bankDTO) {
        Bank bank = new Bank();
        bank.setFullName(bankDTO.getFullName());
        bank.setAbbreviation(bankDTO.getAbbreviation());
        bank.setActive(true); // Default to true
        Bank savedBank = bankRepository.save(bank);
        return mapToDTO(savedBank);
    }

    @Override
    public BankResponseDTO updateBank(BankResponseDTO bankDTO, Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));

        bank.setFullName(bankDTO.getFullName());
        bank.setAbbreviation(bankDTO.getAbbreviation());
        bank.setActive(bankDTO.isActive());

        Bank updatedBank = bankRepository.save(bank);
        return mapToDTO(updatedBank);
    }

    @Override
    public void deactivateBank(Long bankId) {
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        bank.setActive(false); // Set isActive to false
        bankRepository.save(bank);
    }

    @Override
    public PagedResponse<BankResponseDTO> getAllBanks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bank> banksPage = bankRepository.findAll(pageable);

        List<BankResponseDTO> bankDTOs = banksPage.stream()
                .filter(Bank::isActive) // Only return active banks
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PagedResponse<>(bankDTOs, banksPage.getNumber(), banksPage.getSize(),
                banksPage.getTotalElements(), banksPage.getTotalPages(), banksPage.isLast());
    }

    private BankResponseDTO mapToDTO(Bank bank) {
        BankResponseDTO dto = new BankResponseDTO();
        dto.setBankId(bank.getBankId());
        dto.setFullName(bank.getFullName());
        dto.setAbbreviation(bank.getAbbreviation());
        dto.setActive(bank.isActive());
        return dto;
    }
}
