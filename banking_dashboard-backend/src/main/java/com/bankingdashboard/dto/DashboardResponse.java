package com.bankingdashboard.dto;

import com.bankingdashboard.entity.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {
    private String name;
    private String email;
    private String accountNumber;
    private BigDecimal balance; 
    private List<Transaction> recentTransactions;
}
