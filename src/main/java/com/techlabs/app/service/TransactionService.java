package com.techlabs.app.service;

import com.techlabs.app.dto.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {

    TransactionResponseDTO getTransactionById(Long transactionId);

    TransactionResponseDTO createNewTransaction(TransactionResponseDTO transactionDTO);

    TransactionResponseDTO updateTransaction(TransactionResponseDTO transactionDTO, Long transactionId);

    void deleteTransaction(Long transactionId);

    List<TransactionResponseDTO> getAllTransactions();
}
