package com.customerfeedback.customer.feedback.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerfeedback.customer.feedback.dto.AuthResponse;
import com.customerfeedback.customer.feedback.dto.LoginRequest;
import com.customerfeedback.customer.feedback.dto.RegisterRequest;
import com.customerfeedback.customer.feedback.service.AuthService;

import lombok.RequiredArgsConstructor;


    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}