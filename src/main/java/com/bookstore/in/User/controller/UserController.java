package com.bookstore.in.User.controller;

import com.bookstore.in.User.model.User;
import com.bookstore.in.User.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getUser(
        @AuthenticationPrincipal User user
    ) {
        return userService.findById(user.getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }
}
