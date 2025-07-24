package com.bankingdashboard.controller;

import com.bankingdashboard.dto.AuthRequest;
import com.bankingdashboard.dto.AuthResponse;
import com.bankingdashboard.dto.UserRegistrationRequest;
import com.bankingdashboard.entity.User;
import com.bankingdashboard.security.JwtService;
import com.bankingdashboard.security.CustomerUserDetails;
import com.bankingdashboard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRegistrationRequest request) {
        User user = userService.registerUser(request); 
        String token = jwtService.generateToken(user); 

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "User registered successfully");
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails.getUser());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

}

