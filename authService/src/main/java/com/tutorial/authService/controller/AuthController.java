package com.tutorial.authService.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tutorial.authService.dto.UserRequest;
import com.tutorial.authService.models.User;
import com.tutorial.authService.producer.AuthEventProducer;
import com.tutorial.authService.services.AuthService;

import jakarta.validation.Valid;
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
    private final AuthEventProducer authEventProducer;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        User savedUser = authService.register(newUser);
        authEventProducer.sendUserEvent(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                "USER_REGISTERED");
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.login(userRequest));
    }
}
