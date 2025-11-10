package com.tutorial.authService.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tutorial.authService.dto.UserRequest;
import com.tutorial.authService.models.User;
import com.tutorial.authService.services.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRequest user) {
        User savedUser = new User();
        savedUser.setName(user.getName());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(user.getPassword());
        return ResponseEntity.ok(authService.register(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.login(userRequest));
    }
}
