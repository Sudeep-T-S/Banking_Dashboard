package com.bankingdashboard.repository;

import com.bankingdashboard.entity.Transaction;
import com.bankingdashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findTop5ByUserOrderByTimestampDesc(User user);
}
