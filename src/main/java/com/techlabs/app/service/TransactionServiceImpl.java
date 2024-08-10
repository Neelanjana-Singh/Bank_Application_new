package com.techlabs.app.service;

import com.techlabs.app.dto.TransactionResponseDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Transaction;
import com.techlabs.app.exception.ResourceNotFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void deleteTransaction(Long transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new ResourceNotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public List<TransactionResponseDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private TransactionResponseDTO mapToDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionTimestamp(transaction.getTransactionTimestamp());
        dto.setAmount(transaction.getAmount());
        dto.setSenderAccountNumber(transaction.getSenderAccountNumber().getAccountNumber());
        dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber().getAccountNumber());
        return dto;
    }
}
