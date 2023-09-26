package com.surveyking.apigateway.authentication.service;

import com.surveyking.apigateway.authentication.model.*;
import com.surveyking.apigateway.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public boolean register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(null)
                .isActive(false)
                .build();
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        } else {
            repository.save(user);
            return true;
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("User not registered"));
        if (!user.getIsActive()) {
            throw new Exception("User not approved by admin");
        } else {
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }
}
