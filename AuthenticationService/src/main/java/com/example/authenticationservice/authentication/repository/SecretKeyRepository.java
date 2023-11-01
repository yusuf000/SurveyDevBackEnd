package com.example.authenticationservice.authentication.repository;

import com.example.authenticationservice.authentication.model.SecretKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretKeyRepository extends JpaRepository<SecretKey, Long> {
}
