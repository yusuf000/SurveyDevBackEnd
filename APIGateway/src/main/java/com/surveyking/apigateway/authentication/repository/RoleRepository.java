package com.surveyking.apigateway.authentication.repository;

import com.surveyking.apigateway.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
