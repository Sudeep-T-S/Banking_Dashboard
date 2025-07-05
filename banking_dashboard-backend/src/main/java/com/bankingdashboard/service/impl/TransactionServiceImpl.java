package com.barclaysbanking.service.impl;

import com.barclaysbanking.dto.TransactionRequest;
import com.barclaysbanking.entity.Transaction;
import com.barclaysbanking.entity.User;
import com.barclaysbanking.exception.ApiException;
import com.barclaysbanking.repository.TransactionRepository;
import com.barclaysbanking.repository.UserRepository;
import com.barclaysbanking.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction deposit(TransactionRequest request) {
        User user = getUserByEmail(request.getEmail());
        BigDecimal amount = request.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Deposit amount must be greater than 0", 400);
        }

        user.setBalance(user.getBalance().add(amount));

        Transaction transaction = Transaction.builder()
                .type("DEPOSIT")
                .amount(amount) 
                .timestamp(LocalDateTime.now())
                .user(user)
                .description(
                    request.getDescription() != null
                        ? request.getDescription()
                        : "Deposit to account"
                )
                .build();

        userRepository.save(user);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction withdraw(TransactionRequest request) {
        User user = getUserByEmail(request.getEmail());
        BigDecimal amount = request.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Withdrawal amount must be greater than 0", 400);
        }

        if (user.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient balance", 400);
        }

        user.setBalance(user.getBalance().subtract(amount));

        Transaction transaction = Transaction.builder()
                .type("WITHDRAW")
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .user(user)
                .description(request.getDescription() != null ? request.getDescription() : "Withdraw from account")
                .build();

        userRepository.save(user);
        return transactionRepository.save(transaction);
    }

    @Override
    public void transfer(TransactionRequest request) {
        String senderEmail = request.getEmail();
        String receiverEmail = request.getRecipientEmail();

        if (senderEmail.equalsIgnoreCase(receiverEmail)) {
            throw new ApiException("Cannot transfer to the same account", 400);
        }

        User sender = getUserByEmail(senderEmail);
        User receiver = getUserByEmail(receiverEmail);
        BigDecimal amount = request.getAmount();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Transfer amount must be greater than 0", 400);
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient balance", 400);
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction debitTransaction = Transaction.builder()
                .user(sender)
                .type("DEBIT")
                .amount(amount)
                .description(request.getDescription() != null ? request.getDescription() : "Transfer to " + receiver.getEmail())
                .timestamp(LocalDateTime.now())
                .build();

        Transaction creditTransaction = Transaction.builder()
                .user(receiver)
                .type("CREDIT")
                .amount(amount)
                .description("Received from " + sender.getEmail())
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);
    }

    @Override
    public List<Transaction> getUserTransactions(String email) {
        User user = getUserByEmail(email);
        return transactionRepository.findTop5ByUserOrderByTimestampDesc(user);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found", 404));
    }
}
