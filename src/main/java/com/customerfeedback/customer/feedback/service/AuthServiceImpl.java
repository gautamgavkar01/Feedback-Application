package com.customerfeedback.customer.feedback.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.customerfeedback.customer.feedback.dto.AuthResponse;
import com.customerfeedback.customer.feedback.dto.LoginRequest;
import com.customerfeedback.customer.feedback.dto.RegisterRequest;
import com.customerfeedback.customer.feedback.entity.Role;
import com.customerfeedback.customer.feedback.entity.UserEntity;
import com.customerfeedback.customer.feedback.repository.UserRepository;
import com.customerfeedback.customer.feedback.security.JwtService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) 
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token, user.getRole().toString());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token, user.getRole().toString());
    }
    
    
    @PostConstruct
    public void createAdminUserIfNotExists() {
        String adminUsername = "admin";
        String adminEmail = "admin@gmail.com";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            UserEntity admin = UserEntity.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
            System.out.println("Admin user created with username: admin and password: admin123");
        }
    }


}
