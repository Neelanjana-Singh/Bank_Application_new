package com.techlabs.app.service;

import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.util.PagedResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionResponseDTO getTransactionById(Long transactionId);

    TransactionResponseDTO createNewTransaction(TransactionResponseDTO transactionDTO);

    TransactionResponseDTO updateTransaction(TransactionResponseDTO transactionDTO, Long transactionId);

    void deactivateTransaction(Long transactionId);

    PagedResponse<TransactionResponseDTO> getAllTransactions(int page, int size);

    PagedResponse<TransactionResponseDTO> getAllTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate, int page, int size);
}
