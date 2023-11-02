package com.example.authenticationservice.authentication.bootstrap;

import com.example.authenticationservice.authentication.model.Privilege;
import com.example.authenticationservice.authentication.model.Role;
import com.example.authenticationservice.authentication.model.User;
import com.example.authenticationservice.authentication.repository.PrivilegeRepository;
import com.example.authenticationservice.authentication.repository.RoleRepository;
import com.example.authenticationservice.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.example.authenticationservice.authentication.constants.PrivilegeConstants.*;
import static com.example.authenticationservice.authentication.constants.RoleConstants.SUPER_ADMIN;

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
                Privilege.builder().name(LANGUAGE_CREATE).build(),
                Privilege.builder().name(LANGUAGE_INFO).build(),
                Privilege.builder().name(CHOICE_CREATE).build(),
                Privilege.builder().name(CHOICE_DELETE).build(),
                Privilege.builder().name(CHOICE_INFO).build(),
                Privilege.builder().name(QUESTION_CREATE).build(),
                Privilege.builder().name(QUESTION_DELETE).build(),
                Privilege.builder().name(QUESTION_INFO).build(),
                Privilege.builder().name(QUESTION_TYPE_CREATE).build(),
                Privilege.builder().name(QUESTION_TYPE_INFO).build(),
                Privilege.builder().name(ANSWER_INFO).build()
        );
    }

    private void createAdminRole() {
        Role roleAdmin = Role.builder()
                .name(SUPER_ADMIN)
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
                .roles(Set.of(roleRepository.findByName(SUPER_ADMIN)))
                .build();
        userRepository.save(userAdmin);
    }
}
