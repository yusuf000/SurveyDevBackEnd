package com.example.authenticationservice.authentication.controller;

import com.example.authenticationservice.authentication.model.AuthenticationResponse;
import com.example.authenticationservice.authentication.model.PasswordChangeRequest;
import com.example.authenticationservice.authentication.model.RegisterRequest;
import com.example.authenticationservice.authentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate() throws Exception {
        return ResponseEntity.ok(service.generateJWT());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(
            @RequestParam("email") String userEmail) {
        return ResponseEntity.ok(service.resetPassword(userEmail));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(
            @RequestBody PasswordChangeRequest passwordChangeRequest
            ){
        return ResponseEntity.ok(service.changePassword(passwordChangeRequest));
    }
}
