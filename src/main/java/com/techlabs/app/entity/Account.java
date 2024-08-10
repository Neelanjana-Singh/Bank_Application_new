package com.techlabs.app.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountNumber;

	@Column(nullable = false)
	private double balance;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bank;

	@OneToMany(mappedBy = "senderAccountNumber", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> sentTransactions;

	@OneToMany(mappedBy = "receiverAccountNumber", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaction> receivedTransactions;

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<Transaction> getSentTransactions() {
		return sentTransactions;
	}

	public void setSentTransactions(List<Transaction> sentTransactions) {
		this.sentTransactions = sentTransactions;
	}

	public List<Transaction> getReceivedTransactions() {
		return receivedTransactions;
	}

	public void setReceivedTransactions(List<Transaction> receivedTransactions) {
		this.receivedTransactions = receivedTransactions;
	}

	public Account(Long accountNumber, double balance, Customer customer, Bank bank, List<Transaction> sentTransactions,
			List<Transaction> receivedTransactions) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customer = customer;
		this.bank = bank;
		this.sentTransactions = sentTransactions;
		this.receivedTransactions = receivedTransactions;
	}

	public Account() {

	}

}