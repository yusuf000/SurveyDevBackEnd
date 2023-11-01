package com.example.authenticationservice.authentication.repository;

import com.example.authenticationservice.authentication.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
