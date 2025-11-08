package com.tutorial.userService.services;

import org.springframework.stereotype.Service;

import com.tutorial.userService.model.User;
import com.tutorial.userService.repositories.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(@NonNull User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }
}
