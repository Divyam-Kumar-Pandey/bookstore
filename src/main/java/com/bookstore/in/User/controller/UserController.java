package com.bookstore.in.User.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.in.ApiResponse;
import com.bookstore.in.User.dto.UpdateUserRequest;
import com.bookstore.in.User.model.User;
import com.bookstore.in.User.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<User> getUser(
        @AuthenticationPrincipal User user
    ) {
        return ApiResponse.success("User fetched successfully", userService.findById(user.getId()));
    }

    @PatchMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<User> updateMe(
        @AuthenticationPrincipal User currentUser,
        @Valid @RequestBody UpdateUserRequest request
    ) {
        return ApiResponse.success("User updated successfully", userService.updateSelf(currentUser, request));
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<User>> getUsers() {
        return ApiResponse.success("Users fetched successfully", userService.getAllUsers());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<User> create(@Valid @RequestBody User user) {
        return ApiResponse.success("User created successfully", userService.createUser(user));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<User> updateByAdmin(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest request
    ) {
        return ApiResponse.success("User updated successfully", userService.updateByAdmin(id, request));
    }
}
