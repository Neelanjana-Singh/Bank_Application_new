package com.techlabs.app.repository;

import com.techlabs.app.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findBySenderAccountNumberAccountNumber(Long senderAccountNumber);

	List<Transaction> findByReceiverAccountNumberAccountNumber(Long receiverAccountNumber);

//	List<Transaction> findBySenderAccountNumber_Customer_User_Username(String username);

//	List<Transaction> findByReceiverAccountNumber_Customer_User_Username(String username);
}
