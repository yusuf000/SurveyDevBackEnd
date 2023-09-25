package com.example.apigateway.authentication.repository;

import com.example.apigateway.authentication.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
