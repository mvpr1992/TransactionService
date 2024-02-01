package com.example.transferServices.transferController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transferServices.model.entity.Transaction;
import com.example.transferServices.service.TransferService;

@RestController
public class TransferController {

    
    protected static final Logger logger = Logger.getLogger(TransferController.class.getName());
    protected TransferService transferService;

   
    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }
    
    @GetMapping(path = "/gettransaction/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Transaction findById(@PathVariable("customerId") int customerId) {
        logger.info(String.format("customer-service findById() invoked:{0} for {1} ", transferService.getClass().getName(), customerId));
        
        Transaction transaction = null;
        try {
        	transaction = transferService.findbycustomerId(customerId);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception raised findByUserId REST Call {0}", ex);
        }
		return transaction;   
    }
    
   
    @PostMapping("/add/{customerId}/{email}/{transactiontype}/{amount}")
    public Transaction addTransaction(@PathVariable("customerId") int customerId, @PathVariable("email") String email,
    		@PathVariable("transactiontype") String transactiontype, @PathVariable("amount") double amount) {
        
    	logger.info(String.format("transaction-service addTransaction invoked:{0} for {1} ", transferService.getClass().getName(), customerId));
        Transaction transaction = null;
        try {
        	 transaction = transferService.addTransaction(customerId,email,transactiontype,amount);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception raised findByUserId REST Call {0}", ex);  
        }
        return transaction;
    }
    
    @GetMapping("/gettransactionlist/{customerList}")
	public List<Transaction> getTransactionList(@PathVariable("customerList") List<Integer> customerList) {
    	
    	logger.info(String.format("transaction-service getTransactionList invoked:{0} for {1} ", transferService.getClass().getName(), customerList));
        
    	List<Transaction> transactionlist = null;
        try {
        	transactionlist = transferService.findbycustomerIdList(customerList);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception raised findByUserId REST Call {0}", ex);  
        }
        System.out.println("transactionlist size: " + transactionlist.size());
    	return transactionlist;
    }
    
    @PostMapping("/savetransactionlist/{newtransactionlist}")
	public void savetransactionlist(@PathVariable("newtransactionlist") List<Transaction> newtransactionlist) {
    	
    	logger.info(String.format("transaction-service savetransactionlist invoked:{0} for {1} ", transferService.getClass().getName(), newtransactionlist));
        
        try {
        	transferService.savetransactionlist(newtransactionlist);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception raised findByUserId REST Call {0}", ex);  
        }
    }
    
    @PostMapping("/transfer/{customerId}/{payeeId}/{amount}/{customeremail}/{payeeemail}")
	public Transaction transfertopayee(@PathVariable("customerId") int customerId,
    		@PathVariable("payeeId") int payeeId, @PathVariable("amount") double amount,
    		@PathVariable("customeremail") String customeremail, @PathVariable("payeeemail") String payeeemail) {
    	
    	logger.info(String.format("transaction-service transfertopayee invoked:{0} for {1} ", transferService.getClass().getName(), customerId));
    	Transaction t = null;
        try {
        	 t = transferService.transfertopayee(customerId,payeeId,amount,customeremail,payeeemail);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception raised findByUserId REST Call {0}", ex);  
        }
        return t;
    }
	
}