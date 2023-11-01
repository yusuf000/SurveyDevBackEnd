package com.surveyking.apigateway.repository;

import com.surveyking.apigateway.model.SecretKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretKeyRepository extends JpaRepository<SecretKey, Long> {
}
