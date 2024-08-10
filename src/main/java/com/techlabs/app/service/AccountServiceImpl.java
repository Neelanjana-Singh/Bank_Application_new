package com.techlabs.app.service;

import com.techlabs.app.dto.AccountResponseDTO;
import com.techlabs.app.entity.Account;
import com.techlabs.app.entity.Bank;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.exception.AdminRelatedException;
import com.techlabs.app.repository.AccountRepository;
import com.techlabs.app.repository.BankRepository;
import com.techlabs.app.repository.CustomerRepository;
import com.techlabs.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// Convert DTO to Entity
		Account account = new Account();
		// account.setAccountNumber(accountResponseDTO.getAccountNumber());
		account.setBalance(accountResponseDTO.getBalance());

		Optional<Customer> customer = customerRepository.findById(accountResponseDTO.getCustomerId());
		if (customer.isPresent()) {
			account.setCustomer(customer.get());
		} else {
			throw new RuntimeException("Customer not found");
		}

		Optional<Bank> bank = bankRepository.findById(accountResponseDTO.getBankId());
		if (bank.isPresent()) {
			account.setBank(bank.get());
		} else {
			throw new RuntimeException("Bank not found");
		}

		// Save Entity
		Account savedAccount = accountRepository.save(account);
		String subject = "Creation of account";
		String text = "Account created successfully with " + savedAccount.getAccountNumber() + "and Balance is "
				+ savedAccount.getBalance() + " and customer id is " + savedAccount.getCustomer().getCustomerId();
		emailservice.sendMail(savedAccount.getCustomer().getUser().getEmail(), subject, text);

		// Convert Entity back to DTO
		AccountResponseDTO savedAccountResponseDTO = new AccountResponseDTO();
		savedAccountResponseDTO.setAccountNumber(savedAccount.getAccountNumber());
		savedAccountResponseDTO.setBalance(savedAccount.getBalance());
		savedAccountResponseDTO.setCustomerId(savedAccount.getCustomer().getCustomerId());
		savedAccountResponseDTO.setBankId(savedAccount.getBank().getBankId());

		return savedAccountResponseDTO;
	}

	@Override
	public void delete(Long accountNumber) {
		accountRepository.deleteById(accountNumber);
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

		// Save Entity
		Account savedAccount = accountRepository.save(account);

		// Convert Entity back to DTO
		AccountResponseDTO savedAccountResponseDTO = new AccountResponseDTO();
		savedAccountResponseDTO.setAccountNumber(savedAccount.getAccountNumber());
		savedAccountResponseDTO.setBalance(savedAccount.getBalance());
		savedAccountResponseDTO.setCustomerId(savedAccount.getCustomer().getCustomerId());
		savedAccountResponseDTO.setBankId(savedAccount.getBank().getBankId());

		return savedAccountResponseDTO;

	}
}