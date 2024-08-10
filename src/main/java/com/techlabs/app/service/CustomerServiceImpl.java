package com.techlabs.app.service;

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.dto.CustomerResponseDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.exception.ResourceNotFoundException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.sql.DataSource;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private  DataSource dataSource;

	
	public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository,
			DataSource dataSource) {
		super();
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
		this.dataSource = dataSource;
	}

	@Override
	public CustomerResponseDTO getCustomerById(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		return mapToDTO(customer);
	}

	@Override
	public CustomerResponseDTO depositToAccount(Long customerId, Long accountId, double amount) {
		Account account = getAccountByCustomerIdAndAccountId(customerId, accountId);
		account.setBalance(account.getBalance() + amount);
		accountRepository.save(account);

		updateTotalBalance(customerId);

		return getCustomerById(customerId);
	}

	@Override
	public CustomerResponseDTO withdrawFromAccount(Long customerId, Long accountId, double amount) {
		Account account = getAccountByCustomerIdAndAccountId(customerId, accountId);
		if (account.getBalance() < amount) {
			throw new IllegalArgumentException("Insufficient balance");
		}
		account.setBalance(account.getBalance() - amount);
		accountRepository.save(account);

		updateTotalBalance(customerId);

		return getCustomerById(customerId);
	}

	@Override
	public double getTotalBalance(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		return customer.getTotalBalance();
	}

	private Account getAccountByCustomerIdAndAccountId(Long customerId, Long accountId) {
		Optional<Account> accountOpt = accountRepository.findById(accountId);
		if (accountOpt.isPresent()) {
			Account account = accountOpt.get();
			if (account.getCustomer().getCustomerId().equals(customerId)) {
				return account;
			} else {
				throw new ResourceNotFoundException("Account does not belong to the customer");
			}
		} else {
			throw new ResourceNotFoundException("Account not found");
		}
	}

	private void updateTotalBalance(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		double totalBalance = customer.getAccounts().stream().mapToDouble(Account::getBalance).sum();
		customer.setTotalBalance(totalBalance);
		customerRepository.save(customer);
	}

	private CustomerResponseDTO mapToDTO(Customer customer) {
		CustomerResponseDTO dto = new CustomerResponseDTO();
		dto.setCustomerId(customer.getCustomerId());
		dto.setFirstName(customer.getFirstName());
		dto.setLastName(customer.getLastName());
		dto.setTotalBalance(customer.getTotalBalance());
		dto.setAccounts(customer.getAccounts().stream()
				.map(account -> new AccountResponseDTO(account.getAccountNumber(), account.getBalance())).toList());
		return dto;
	}
}
