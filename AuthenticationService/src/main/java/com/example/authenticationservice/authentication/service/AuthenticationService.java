package com.example.authenticationservice.authentication.service;

import com.example.authenticationservice.authentication.model.*;
import com.example.authenticationservice.authentication.repository.PasswordResetTokenRepository;
import com.example.authenticationservice.authentication.repository.RoleRepository;
import com.example.authenticationservice.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.authenticationservice.authentication.constants.RoleConstants.ADMIN;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final JavaMailSender emailSender;

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

    public Boolean resetPassword(String userEmail) {
        Optional<User> user = userRepository.findByUserId(userEmail);
        if (user.isEmpty()) {
            return false;
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user.get(), token);
        emailSender.send(constructResetTokenEmail(token, user.get()));
        return true;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        Optional<PasswordResetToken> previousToken = passwordResetTokenRepository.findByUser(user);
        previousToken.ifPresent(passwordResetTokenRepository::delete);
        PasswordResetToken myToken = PasswordResetToken.builder().user(user).token(token).expiryDate(new Date(new Date().getTime() + (1000 * 60 * 10))).build();
        passwordResetTokenRepository.save(myToken);
    }

    private SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String url = "http://localhost:8080/user/changePassword?token=" + token;
        String message = "Dear "+user.getName()+",\n"+"You've requested a password reset. Reset your password by following this link: "+url+" \n"+"Thanks\n"+"SurveyDevs";
        return constructEmail("Reset Password", message , user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getUserId());
        email.setFrom("surveydevs@gmail.com");
        return email;
    }
}
