package com.barclaysbanking.repository;

import com.barclaysbanking.entity.Transaction;
import com.barclaysbanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findTop5ByUserOrderByTimestampDesc(User user);
}
