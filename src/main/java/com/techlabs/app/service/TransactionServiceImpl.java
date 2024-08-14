package com.techlabs.app.service;

import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.exception.AdminRelatedException;
import com.techlabs.app.exception.ResourceNotFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.TransactionRepository;
import com.techlabs.app.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public TransactionResponseDTO getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return mapToDTO(transaction);
    }

    @Override
    public TransactionResponseDTO createNewTransaction(TransactionResponseDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionTimestamp(LocalDateTime.now());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setActive(true); // Set default active status

        Optional<Account> senderAccountOpt = accountRepository.findById(transactionDTO.getSenderAccountNumber());
        Optional<Account> receiverAccountOpt = accountRepository.findById(transactionDTO.getReceiverAccountNumber());

        if (senderAccountOpt.isPresent() && receiverAccountOpt.isPresent()) {
            Account senderAccount = senderAccountOpt.get();
            Account receiverAccount = receiverAccountOpt.get();

            // Update balances
            double newSenderBalance = senderAccount.getBalance() - transactionDTO.getAmount();
            if (newSenderBalance < 0) {
                throw new AdminRelatedException("Insufficient funds in sender's account");
            }
            senderAccount.setBalance(newSenderBalance);

            double newReceiverBalance = receiverAccount.getBalance() + transactionDTO.getAmount();
            receiverAccount.setBalance(newReceiverBalance);

            // Save updated accounts
            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);

            // Set accounts to the transaction
            transaction.setSenderAccountNumber(senderAccount);
            transaction.setReceiverAccountNumber(receiverAccount);
        } else {
            throw new ResourceNotFoundException("Sender or receiver account not found");
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToDTO(savedTransaction);
    }

    @Override
    public TransactionResponseDTO updateTransaction(TransactionResponseDTO transactionDTO, Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        transaction.setAmount(transactionDTO.getAmount());

        Optional<Account> senderAccount = accountRepository.findById(transactionDTO.getSenderAccountNumber());
        if (senderAccount.isPresent()) {
            transaction.setSenderAccountNumber(senderAccount.get());
        } else {
            throw new ResourceNotFoundException("Sender account not found");
        }

        Optional<Account> receiverAccount = accountRepository.findById(transactionDTO.getReceiverAccountNumber());
        if (receiverAccount.isPresent()) {
            transaction.setReceiverAccountNumber(receiverAccount.get());
        } else {
            throw new ResourceNotFoundException("Receiver account not found");
        }

        Transaction updatedTransaction = transactionRepository.save(transaction);
        return mapToDTO(updatedTransaction);
    }

    @Override
    public void deactivateTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        transaction.setActive(false); // Set isActive to false
        transactionRepository.save(transaction);
    }

    @Override
    public PagedResponse<TransactionResponseDTO> getAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);

        List<TransactionResponseDTO> content = transactionPage.getContent().stream()
                .filter(Transaction::isActive)
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PagedResponse<>(content, page, size, transactionPage.getTotalElements(), transactionPage.getTotalPages(), transactionPage.isLast());
    }

    @Override
    public PagedResponse<TransactionResponseDTO> getAllTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository.findByTransactionTimestampBetween(startDate, endDate, pageable);

        List<TransactionResponseDTO> content = transactionPage.getContent().stream()
                .filter(Transaction::isActive)
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new PagedResponse<>(content, page, size, transactionPage.getTotalElements(), transactionPage.getTotalPages(), transactionPage.isLast());
    }

    private TransactionResponseDTO mapToDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionTimestamp(transaction.getTransactionTimestamp());
        dto.setAmount(transaction.getAmount());
        dto.setSenderAccountNumber(transaction.getSenderAccountNumber().getAccountNumber());
        dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber().getAccountNumber());
        dto.setActive(transaction.isActive()); // Include isActive field
        return dto;
    }
}
