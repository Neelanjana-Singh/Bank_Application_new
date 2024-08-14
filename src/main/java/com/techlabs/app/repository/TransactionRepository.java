package com.techlabs.app.repository;

import com.techlabs.app.entity.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findBySenderAccountNumberAccountNumber(Long senderAccountNumber);

	List<Transaction> findByReceiverAccountNumberAccountNumber(Long receiverAccountNumber);

	List<Transaction> findAllByTransactionTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Transaction> findByTransactionTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

	Page<Transaction> findByTransactionTimestampBetween(LocalDateTime startDate, LocalDateTime endDate,
			Pageable pageable);

//	List<Transaction> findBySenderAccountNumber_Customer_User_Username(String username);

//	List<Transaction> findByReceiverAccountNumber_Customer_User_Username(String username);
}
