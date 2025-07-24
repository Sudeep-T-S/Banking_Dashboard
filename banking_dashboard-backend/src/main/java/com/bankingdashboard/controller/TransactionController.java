package com.bankingdashboard.controller;

import com.bankingdashboard.dto.TransactionRequest;
import com.bankingdashboard.entity.Transaction;
import com.bankingdashboard.service.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> handleTransaction(@RequestBody TransactionRequest request) {
        switch (request.getType().toUpperCase()) {
            case "DEPOSIT" -> {
                Transaction depositTxn = transactionService.deposit(request);
                return ResponseEntity.ok(depositTxn);
            }
            case "WITHDRAW" -> {
                Transaction withdrawTxn = transactionService.withdraw(request);
                return ResponseEntity.ok(withdrawTxn);
            }
            case "TRANSFER" -> {
                transactionService.transfer(request);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Transfer successful");
                response.put("timestamp", LocalDateTime.now());
                return ResponseEntity.ok(response);
            }
            default -> {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid transaction type"));
            }
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.withdraw(request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transfer(@RequestBody TransactionRequest request) {
        transactionService.transfer(request);
        return ResponseEntity.ok(Map.of(
                "message", "Transfer successful",
                "timestamp", LocalDateTime.now()
        ));
    }

    @GetMapping("/history/{email}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable String email) {
        List<Transaction> transactions = transactionService.getUserTransactions(email);
        return ResponseEntity.ok(transactions);
    }
}


