package com.barclaysbanking.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {
    private String name;
    private String email;
    private String password;
}
