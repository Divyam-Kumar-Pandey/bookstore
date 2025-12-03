package com.bookstore.in.Auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bookstore.in.Auth.dto.AuthResponse;
import com.bookstore.in.Auth.dto.LoginRequest;
import com.bookstore.in.Auth.dto.RegisterRequest;
import com.bookstore.in.Security.JwtService;
import com.bookstore.in.User.model.User;
import com.bookstore.in.User.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public static enum UserRole {
        ADMIN,
        USER
    }

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User newUser = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .build();

        User savedUser = userService.createUser(newUser);
			String accessToken = jwtService.generateAccessToken(savedUser);
			String refreshToken = jwtService.generateRefreshToken(savedUser);
			return buildResponse(savedUser, accessToken, refreshToken);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userService.findByEmail(request.email());
			String accessToken = jwtService.generateAccessToken(user);
			String refreshToken = jwtService.generateRefreshToken(user);
			return buildResponse(user, accessToken, refreshToken);
    }

		public AuthResponse refresh(String refreshToken) {
			String email = jwtService.extractUsername(refreshToken);
			User user = userService.findByEmail(email);
			if (!jwtService.isRefreshTokenValid(refreshToken, user)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
			}
			String newAccessToken = jwtService.generateAccessToken(user);
			String newRefreshToken = jwtService.generateRefreshToken(user); // rotate
			return buildResponse(user, newAccessToken, newRefreshToken);
		}

		private AuthResponse buildResponse(User user, String accessToken, String refreshToken) {
			return new AuthResponse(accessToken, refreshToken, user.getId(), user.getFirstName(), user.getEmail());
    }
}

