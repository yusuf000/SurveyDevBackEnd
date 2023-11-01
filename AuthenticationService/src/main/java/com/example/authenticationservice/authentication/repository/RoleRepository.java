package com.example.authenticationservice.authentication.repository;

import com.example.authenticationservice.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
