package com.bookstore.in.Auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.in.Auth.dto.AuthResponse;
import com.bookstore.in.Auth.dto.LoginRequest;
import com.bookstore.in.Auth.dto.RefreshTokenRequest;
import com.bookstore.in.Auth.dto.RegisterRequest;
import com.bookstore.in.Auth.service.AuthService;
import com.bookstore.in.ApiResponse;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ApiResponse.success("User registered successfully", authService.register(request));
	}

	@PostMapping("/login")
	public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ApiResponse.success("User logged in successfully", authService.login(request));
	}
	
	@PostMapping("/refresh")
	public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
		return ApiResponse.success("Token refreshed successfully", authService.refresh(request.refreshToken()));
	}
}

