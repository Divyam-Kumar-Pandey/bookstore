package com.bookstore.in.auth.controller;

import com.bookstore.in.auth.dto.AuthResponse;
import com.bookstore.in.auth.dto.LoginRequest;
import com.bookstore.in.auth.dto.RegisterRequest;
import com.bookstore.in.auth.dto.RefreshTokenRequest;
import com.bookstore.in.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
		return authService.register(request);
	}

	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}
	
	@PostMapping("/refresh")
	public AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
		return authService.refresh(request.refreshToken());
	}
}

