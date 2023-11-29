package com.example.authenticationservice.authentication.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String userId;
    private String password;
}
