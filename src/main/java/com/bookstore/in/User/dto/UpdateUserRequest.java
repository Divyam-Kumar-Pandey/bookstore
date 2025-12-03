package com.bookstore.in.User.dto;

import java.time.LocalDate;

import com.bookstore.in.Auth.service.AuthService;

public record UpdateUserRequest(
    String firstName,
    String lastName,
    String avatar,
    LocalDate dateOfBirth,
    String phoneNumber,
    AuthService.UserRole role
) { }


