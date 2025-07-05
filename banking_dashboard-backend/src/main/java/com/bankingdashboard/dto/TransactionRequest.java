package com.barclaysbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String email;             
    private BigDecimal amount;       
    private String type;              
    private String recipientEmail;   
    private String description;      
}
