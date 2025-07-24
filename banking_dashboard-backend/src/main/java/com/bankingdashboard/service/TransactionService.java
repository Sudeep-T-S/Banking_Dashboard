package com.bankingdashboard.service;

import com.bankingdashboard.dto.TransactionRequest;
import com.bankingdashboard.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction deposit(TransactionRequest request);
    Transaction withdraw(TransactionRequest request);
    void transfer(TransactionRequest request);
    List<Transaction> getUserTransactions(String email);
}


