package com.bookstore.in.auth.dto;

public record AuthResponse(
		String accessToken,
		String refreshToken,
		Long userId,
		String name,
		String email
) {
}

