package com.bankingdashboard.controller;

import com.bankingdashboard.dto.*;
import com.bankingdashboard.entity.Transaction;
import com.bankingdashboard.entity.User;
import com.bankingdashboard.exception.ApiException;
import com.bankingdashboard.repository.TransactionRepository;
import com.bankingdashboard.repository.UserRepository;
import com.bankingdashboard.security.JwtService;
import com.bankingdashboard.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        try {
            User user = userService.registerUser(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
        try {
            User user = userService.loginUser(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/{email}/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getDashboard(email));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ApiException("User not found", 404));

        List<Transaction> transactions = transactionRepository.findTop5ByUserOrderByTimestampDesc(user);

        DashboardResponse response = new DashboardResponse();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setAccountNumber(user.getAccountNumber());
        response.setBalance(user.getBalance());
        response.setRecentTransactions(transactions);  

        return ResponseEntity.ok(response);
    }
}
