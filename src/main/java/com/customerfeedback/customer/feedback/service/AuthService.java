package com.customerfeedback.customer.feedback.service;

import com.customerfeedback.customer.feedback.dto.AuthResponse;
import com.customerfeedback.customer.feedback.dto.LoginRequest;
import com.customerfeedback.customer.feedback.dto.RegisterRequest;

public interface AuthService {
	
	AuthResponse register(RegisterRequest registerRequest);
	AuthResponse login(LoginRequest loginRequest);

}