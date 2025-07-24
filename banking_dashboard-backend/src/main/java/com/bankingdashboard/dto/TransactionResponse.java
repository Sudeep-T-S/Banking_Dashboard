package com.bankingdashboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private String type;         
    private double amount;
    private LocalDateTime timestamp;
    private String description;  
}