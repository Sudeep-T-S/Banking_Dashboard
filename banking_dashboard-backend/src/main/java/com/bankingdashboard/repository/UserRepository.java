package com.bankingdashboard.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bankingdashboard.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByAccountNumber(String accountNumber);
}
