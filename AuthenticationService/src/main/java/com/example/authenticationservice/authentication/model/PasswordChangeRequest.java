package com.example.authenticationservice.authentication.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    String password;
    String token;
}
