package com.example.apigateway.authentication.bootstrap;

import com.example.apigateway.authentication.model.Privilege;
import com.example.apigateway.authentication.model.Role;
import com.example.apigateway.authentication.model.User;
import com.example.apigateway.authentication.repository.PrivilegeRepository;
import com.example.apigateway.authentication.repository.RoleRepository;
import com.example.apigateway.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.example.apigateway.authentication.constants.PrivilegeConstants.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (privilegeRepository.count() == 0) populatePrivileges();
        if (roleRepository.count() == 0) createAdminRole();
        if (userRepository.count() == 0) createUserAdmin();
    }

    private void populatePrivileges() {
        privilegeRepository.saveAll(getAllPrivileges());
    }

    private Set<Privilege> getAllPrivileges() {
        return Set.of(Privilege.builder().name(PROJECT_INFO).build(),
                Privilege.builder().name(PROJECT_CREATE).build(),
                Privilege.builder().name(PROJECT_DELETE).build(),
                Privilege.builder().name(PROJECT_UPDATE).build(),
                Privilege.builder().name(UPLOAD_SCRIPT).build(),
                Privilege.builder().name(CHECK_SCRIPT).build(),
                Privilege.builder().name(INTERVIEWER_ASSIGNMENT).build(),
                Privilege.builder().name(INTERVIEWER_INFO).build(),
                Privilege.builder().name(INTERVIEWER_CREATE).build(),
                Privilege.builder().name(INTERVIEWER_DELETE).build(),
                Privilege.builder().name(INTERVIEWER_UPDATE).build(),
                Privilege.builder().name(FIELD_STATUS).build(),
                Privilege.builder().name(DASHBOARD).build(),
                Privilege.builder().name(DATA_BY_RESPONDENT).build(),
                Privilege.builder().name(QUALITY_CONTROL).build(),
                Privilege.builder().name(QC_REPORT).build(),
                Privilege.builder().name(COMMERCIAL_REPORT).build(),
                Privilege.builder().name(DATA_DOWNLOAD).build(),
                Privilege.builder().name(TELEPHONIC_INTERVIEW).build()
        );
    }

    private void createAdminRole() {
        Role roleAdmin = Role.builder()
                .name("Super Admin")
                .privileges(new HashSet<>(privilegeRepository.findAll()))
                .build();
        roleRepository.save(roleAdmin);
    }

    private void createUserAdmin() {
        User userAdmin = User.builder()
                .name("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .isActive(true)
                .roles(Set.of(roleRepository.findByName("Super Admin")))
                .build();
        userRepository.save(userAdmin);
    }
}
