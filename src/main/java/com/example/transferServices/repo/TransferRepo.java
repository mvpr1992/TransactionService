package com.example.transferServices.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.transferServices.model.entity.Transaction;

public interface TransferRepo extends JpaRepository<Transaction, Integer> {
	
	@Query(value="select * from transaction where transaction_id = (select max(transaction_id) from transaction where customer_id = ?1)",nativeQuery = true)
	public Transaction findmaxTransactionUserbyCustomerId(int customerId);	
	
	@Query(value="select * from transaction where transaction_id in (select max(transaction_id) \r\n"
			+ "			from transaction where customer_id in :customerList group by customer_id)", nativeQuery = true)
	public List<Transaction> findByCustomerIdList(List<Integer> customerList);
}