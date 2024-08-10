package com.techlabs.app.repository;

import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerCustomerId(Long customerId);
    List<Account> findByBankBankId(Long bankId);
   // Optional<Account> findByCustomerUserUsername(String username); // Add this method
	//Optional<Transaction> findByUsername(String username);
	//List<Account> findByCustomer_User_Username(String username);
}
