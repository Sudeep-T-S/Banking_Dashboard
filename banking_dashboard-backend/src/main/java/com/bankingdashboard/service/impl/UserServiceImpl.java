package com.barclaysbanking.service.impl;

import com.barclaysbanking.dto.*;
import com.barclaysbanking.entity.User;
import com.barclaysbanking.exception.ApiException;
import com.barclaysbanking.exception.EmailAlreadyExistsException;
import com.barclaysbanking.repository.UserRepository;
import com.barclaysbanking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegistrationRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new EmailAlreadyExistsException("User with this email already exists");
        });

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountNumber(generateAccountNumber())
                .balance(BigDecimal.ZERO) // ✅ Use BigDecimal instead of 0.0
                .role("USER")
                .build();

        return userRepository.save(user);
    }

    @Override
    public User loginUser(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException("Invalid email or password", 401));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid email or password", 401);
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found", 404));
    }

    @Override
    public DashboardResponse getDashboard(String email) {
        User user = getUserByEmail(email);
        return DashboardResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .accountNumber(user.getAccountNumber())
                .balance(user.getBalance()) 
                .recentTransactions(null)
                .build();
    }

    private String generateAccountNumber() {
        return "ACCT-" + UUID.randomUUID().toString().substring(0, 8);
    }
}

