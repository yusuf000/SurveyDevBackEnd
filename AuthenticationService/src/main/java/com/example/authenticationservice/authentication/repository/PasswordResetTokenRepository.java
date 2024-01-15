package com.example.authenticationservice.authentication.repository;

import com.example.authenticationservice.authentication.model.PasswordResetToken;
import com.example.authenticationservice.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUser(User user);
    Optional<PasswordResetToken> findByToken(String token);
}
