package com.techlabs.app.service;

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.exception.AdminRelatedException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.repository.CustomerRepository;

import com.techlabs.app.util.PagedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private EmailService emailservice;

	public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository,
			BankRepository bankRepository, EmailService emailservice) {
		super();
		this.accountRepository = accountRepository;
		this.customerRepository = customerRepository;
		this.bankRepository = bankRepository;
		this.emailservice = emailservice;
	}

	@Override
	public AccountResponseDTO save(AccountResponseDTO accountResponseDTO) {
		Optional<Customer> customer = customerRepository.findById(accountResponseDTO.getCustomerId());
		if (customer.isEmpty()) {
			// account.setCustomer(customer.get());
			throw new RuntimeException("Customer not found");
		}

		Optional<Bank> bank = bankRepository.findById(accountResponseDTO.getBankId());
		if (bank.isEmpty()) {
			// account.setBank(bank.get());
			throw new RuntimeException("Bank not found");
		}

		Customer customer2 = customer.get();
		// Convert DTO to Entity
		Account account = new Account();
		account.setBalance(accountResponseDTO.getBalance());

		account.setActive(true);
		account.setCustomer(customer2);
		account.setBank(bank.get());
		account.setReceivedTransactions(new ArrayList<>());
		account.setSentTransactions(new ArrayList<>());
		Account savedAccount = accountRepository.save(account);
		customer2.getAccounts().add(savedAccount);
		customerRepository.save(customer2);
		AccountResponseDTO savedAccountResponseDTO = new AccountResponseDTO();
		savedAccountResponseDTO.setAccountNumber(savedAccount.getAccountNumber());
		savedAccountResponseDTO.setBalance(savedAccount.getBalance());
		savedAccountResponseDTO.setCustomerId(savedAccount.getCustomer().getCustomerId());
		savedAccountResponseDTO.setBankId(savedAccount.getBank().getBankId());
		savedAccountResponseDTO.setActive(savedAccount.isActive()); // Set isActive
		String text = "creation of account";
		String message = "Account create successfully";
		emailservice.sendMail(customer2.getUser().getEmail(), text, message);

		return savedAccountResponseDTO;
	}

	@Override
	public void deactivate(Long accountNumber) {
		Account account = accountRepository.findById(accountNumber)
				.orElseThrow(() -> new AdminRelatedException("Account not found"));
		account.setActive(false); // Set isActive to false
		accountRepository.save(account);
	}

	@Override
	public List<AccountResponseDTO> findByCustomerId(Long customerId) {
		List<Account> accounts = accountRepository.findByCustomerCustomerId(customerId);
		return accounts.stream().map(account -> {
			AccountResponseDTO dto = new AccountResponseDTO();
			dto.setAccountNumber(account.getAccountNumber());
			dto.setBalance(account.getBalance());
			dto.setCustomerId(account.getCustomer().getCustomerId());
			dto.setBankId(account.getBank().getBankId());
			dto.setActive(account.isActive());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public AccountResponseDTO update(AccountResponseDTO accountDTO, long accountNumber) {
		Account account = accountRepository.findById(accountNumber)
				.orElseThrow(() -> new AdminRelatedException("Account not found"));

		account.setBalance(accountDTO.getBalance());

		Optional<Customer> customer = customerRepository.findById(accountDTO.getCustomerId());
		if (customer.isPresent()) {
			account.setCustomer(customer.get());
		} else {
			throw new RuntimeException("Customer not found");
		}

		Optional<Bank> bank = bankRepository.findById(accountDTO.getBankId());
		if (bank.isPresent()) {
			account.setBank(bank.get());
		} else {
			throw new RuntimeException("Bank not found");
		}

		Account updatedAccount = accountRepository.save(account);
		AccountResponseDTO updatedAccountDTO = new AccountResponseDTO();
		updatedAccountDTO.setAccountNumber(updatedAccount.getAccountNumber());
		updatedAccountDTO.setBalance(updatedAccount.getBalance());
		updatedAccountDTO.setCustomerId(updatedAccount.getCustomer().getCustomerId());
		updatedAccountDTO.setBankId(updatedAccount.getBank().getBankId());
		updatedAccountDTO.setActive(updatedAccount.isActive()); // Set isActive

		return updatedAccountDTO;
	}

	@Override
	public PagedResponse<AccountResponseDTO> findByCustomerId(Long customerId, int page, int size, String sortBy,
			String direction) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
		Page<Account> accountPage = accountRepository.findByCustomerCustomerId(customerId, pageable);

		List<AccountResponseDTO> accountDTOs = accountPage.getContent().stream().map(account -> {
			AccountResponseDTO dto = new AccountResponseDTO();
			dto.setAccountNumber(account.getAccountNumber());
			dto.setBalance(account.getBalance());
			dto.setCustomerId(account.getCustomer().getCustomerId());
			dto.setBankId(account.getBank().getBankId());
			dto.setActive(account.isActive());
			return dto;
		}).collect(Collectors.toList());

		return new PagedResponse<>(accountDTOs, accountPage.getNumber(), accountPage.getSize(),
				accountPage.getTotalElements(), accountPage.getTotalPages(), accountPage.isLast());
	}

}