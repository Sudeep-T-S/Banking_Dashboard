package com.barclaysbanking.service;

import com.barclaysbanking.dto.TransactionRequest;
import com.barclaysbanking.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction deposit(TransactionRequest request);
    Transaction withdraw(TransactionRequest request);
    void transfer(TransactionRequest request);
    List<Transaction> getUserTransactions(String email);
}


