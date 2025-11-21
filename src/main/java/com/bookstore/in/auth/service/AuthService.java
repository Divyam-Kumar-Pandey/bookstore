package com.bookstore.in.auth.service;

import com.bookstore.in.User.model.User;
import com.bookstore.in.User.service.UserService;
import com.bookstore.in.auth.dto.AuthResponse;
import com.bookstore.in.auth.dto.LoginRequest;
import com.bookstore.in.auth.dto.RegisterRequest;
import com.bookstore.in.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                .email(request.email())
                .password(request.password())
                .build();

        User savedUser = userService.createUser(newUser);
        String token = jwtService.generateToken(savedUser);
        return buildResponse(savedUser, token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userService.findByEmail(request.email());
        String token = jwtService.generateToken(user);
        return buildResponse(user, token);
    }

    private AuthResponse buildResponse(User user, String token) {
        return new AuthResponse(token, user.getId(), user.getFirstName(), user.getEmail());
    }
}

