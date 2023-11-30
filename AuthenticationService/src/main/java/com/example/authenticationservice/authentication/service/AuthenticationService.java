package com.example.authenticationservice.authentication.service;

import com.example.authenticationservice.authentication.model.AuthenticationResponse;
import com.example.authenticationservice.authentication.model.Privilege;
import com.example.authenticationservice.authentication.model.RegisterRequest;
import com.example.authenticationservice.authentication.model.User;
import com.example.authenticationservice.authentication.repository.RoleRepository;
import com.example.authenticationservice.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.authenticationservice.authentication.constants.RoleConstants.ADMIN;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public boolean register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(roleRepository.findByName(ADMIN)))
                .build();
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            return false;
        } else {
            userRepository.save(user);
            return true;
        }

    }

    public AuthenticationResponse generateJWT() throws Exception {

        var user = userRepository.findByUserId(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new Exception("User not registered"));

        List<String> authorities = user.getRoles().stream().flatMap(role -> role.getPrivileges().stream().map(Privilege::getName)).collect(Collectors.toList());
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
