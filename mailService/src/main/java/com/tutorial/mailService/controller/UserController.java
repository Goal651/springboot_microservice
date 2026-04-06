package com.tutorial.userService.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.userService.model.User;
import com.tutorial.userService.producer.UserEventProducer;
import com.tutorial.userService.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserEventProducer userEventProducer;

    @GetMapping
    public List<User> getUsers() {

        // userEventProducer.sendUserCreatedEvent(savedUser.getId(),
        // savedUser.getName(), savedUser.getEmail());
        return userService.saveUser();
    }
}
