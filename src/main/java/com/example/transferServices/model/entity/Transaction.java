package com.example.transferServices.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int transactionId;
	@Column
	private int customerId;
	private String transactiontype;
	private double amount;
	private double accountBalance;
	private Date dateofTransaction;
	
	public Transaction() {
	
	} 
    
    public Transaction(int transactionId, int customerId, String transactiontype, double amount,
    		double accountBalance, Date dateofTransaction) {
       
    	this.transactionId = transactionId;
    	this.customerId = customerId;
    	this.transactiontype = transactiontype;
    	this.amount = amount;
    	this.accountBalance = accountBalance;
    	this.dateofTransaction = dateofTransaction;
    }
    
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Date getDateofTransaction() {
		return dateofTransaction;
	}

	public void setDateofTransaction(Date dateofTransaction) {
		this.dateofTransaction = dateofTransaction;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
    public String toString() {
        return new StringBuilder("{customerId: ").append(customerId).append(", accountBalance: ")
                .append(accountBalance).append("}").toString();
    }
}

