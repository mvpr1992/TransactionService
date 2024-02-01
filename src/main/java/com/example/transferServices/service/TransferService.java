package com.example.transferServices.service;

import java.util.List;

import com.example.transferServices.model.entity.Transaction;

public interface TransferService {
   
    public Transaction addTransaction(int customerId,String email,String transactiontype,double amount) throws Exception; 
    
    public Transaction findbycustomerId(int customerId) throws Exception;
    
    public List<Transaction> findbycustomerIdList(List<Integer> customerList) throws Exception;
    
    public void savetransactionlist(List<Transaction> newtransactionlist) throws Exception;
    
    public Transaction transfertopayee(int customerId,int payeeId,double amount,String customeremail,String payeeemail) throws Exception; 
}