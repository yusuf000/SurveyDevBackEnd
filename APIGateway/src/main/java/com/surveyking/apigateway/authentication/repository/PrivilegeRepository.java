package com.surveyking.apigateway.authentication.repository;

import com.surveyking.apigateway.authentication.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
