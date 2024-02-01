package com.example.transferServices.service;


import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.transferServices.model.entity.Transaction;
import com.example.transferServices.repo.TransferRepo;


@Service("transactionService")
public class TransferServiceImpl implements TransferService {

	protected static final Logger logger = Logger.getLogger(TransferServiceImpl.class.getName());
    private TransferRepo transferrepo;
    private EmailSenderService emailservice;

    @Autowired
    public TransferServiceImpl(TransferRepo transferrepo,EmailSenderService emailservice) {
        this.transferrepo = transferrepo;
        this.emailservice = emailservice;
    }
    
    @Override
    public Transaction findbycustomerId(int customerId) throws Exception {
    	 logger.info("In customer service : findbycustomerId mehtod: customerId : " + customerId);
    	 return transferrepo.findmaxTransactionUserbyCustomerId(customerId);
    }
    
    @Override
    public List<Transaction> findbycustomerIdList(List<Integer> customerList) throws Exception {
    	 logger.info("In transfer service : findbycustomerIdList mehtod: customerList : " + customerList);
    	 return transferrepo.findByCustomerIdList(customerList);
    }
    
    @Override
    public Transaction addTransaction(int customerId,String email,String transactiontype,double amount) {
    	
        logger.info("In transaction repository : addTransaction method: UserId : ");
        Transaction transaction = new Transaction();
        
        try {

	       double accountBalance = 0;
	        
	       Transaction t = transferrepo.findmaxTransactionUserbyCustomerId(customerId);
	        
	        if(t != null) {
	        	accountBalance = t.getAccountBalance();
	        } 
	        
	        if("CR".equals(transactiontype) && amount > 0) {
	    			
	    			transaction.setCustomerId(customerId);
	    			transaction.setTransactiontype("CR");
	    			transaction.setAmount(amount);
	    			transaction.setAccountBalance(accountBalance+amount);
	    			Date d = new Date();
	    			transaction.setDateofTransaction(d);
	    			
	    			transaction = transferrepo.save(transaction);
	    			
	    			emailservice.sendEmail(email, "Your Account has been credited for $" + amount + "through cheque deposit and "
	    					+ "your account balance is $" + transaction.getAccountBalance(),
	    					"Your Account has been credited for $" + amount + "through cheque deposit.");
	    			
	        	} else if("DR".equals(transactiontype) && amount > 0 && amount < accountBalance) { 
	    			
	        		transaction.setCustomerId(customerId);
	        		transaction.setTransactiontype("DR");
	        		transaction.setAmount(amount);
	        		transaction.setAccountBalance(accountBalance-amount);
	        		Date d = new Date();
	        		transaction.setDateofTransaction(d);
    			
	        		transaction = transferrepo.save(transaction);
	        		emailservice.sendEmail(email, "Your Account has been debited for $" + amount + "through ATM withdrawl and "
	    					+ "your account balance is $" + transaction.getAccountBalance(),
	    					"Your Account has been debited for $" + amount + "through ATM Withdrawl.");
	        	} else if ("INIT".equals(transactiontype)) { 
    			
	        		transaction.setCustomerId(customerId);
	        		transaction.setTransactiontype("INIT");
	        		transaction.setAmount(0);
	        		transaction.setAccountBalance(0);
	        		Date d = new Date();
	        		transaction.setDateofTransaction(d);
    			
    				transaction = transferrepo.save(transaction);
	        	}
	        } catch (Exception e) {
	        	e.printStackTrace();
        }	
		return transaction;      
    } 
    
    @Override
    public void savetransactionlist(List<Transaction> newtransactionlist) throws Exception {
   	 	logger.info("In transfer service : savetransactionlist mehtod: newtransactionlist : " + newtransactionlist);
   	 	transferrepo.saveAll(newtransactionlist);
    }
    
    public Transaction transfertopayee(int customerId,int payeeId,double amount,String customeremail,String payeeemail) throws Exception {
    	
    	logger.info("In transaction service : transfertopayee method: ");
        
    	Transaction tran1 = new Transaction();
        Transaction tran2 = new Transaction();
        
        try {
	 
	        double accountBalance = 0;
	        
	        Transaction t = transferrepo.findmaxTransactionUserbyCustomerId(customerId);   
	        Transaction t2 = transferrepo.findmaxTransactionUserbyCustomerId(payeeId); 
	        
	        if(t != null) {
	        	accountBalance = t.getAccountBalance();
	        }
	        
	        if(amount > 0 && amount < accountBalance) { 
    			
	        	tran1.setCustomerId(customerId);
	        	tran1.setTransactiontype("TFROUT");
	        	tran1.setAmount(amount);
	        	tran1.setAccountBalance(accountBalance-amount);
        		Date d = new Date();
        		tran1.setDateofTransaction(d);
        		tran1 = transferrepo.save(tran1);
        		emailservice.sendEmail(customeremail, "Your Account has been debited for $" + amount + " for money tranfer and "
    					+ " your account balance is $" + tran1.getAccountBalance(),
    					"Your Payment of $" + amount + " is Success.");
    			
        		
        		tran2.setCustomerId(payeeId);
	        	tran2.setTransactiontype("TFRIN");
	        	tran2.setAmount(amount);
	        	tran2.setAccountBalance(t2.getAccountBalance()+amount);
        		tran2.setDateofTransaction(d);
			
        		tran2 = transferrepo.save(tran2);
        		emailservice.sendEmail(payeeemail, "Your Account has been credited for $" + amount + "through money transfer and "
    					+ "your account balance is $" + tran2.getAccountBalance(),
    					"Your Account has been credited for $" + amount + "through money transfer.");
        	}
	        
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } 
        return tran1;
    }
    
}