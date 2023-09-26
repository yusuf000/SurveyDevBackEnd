package com.surveyking.apigateway.authentication.repository;

import com.surveyking.apigateway.authentication.model.SecretKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretKeyRepository extends JpaRepository<SecretKey, Long> {
}
