package com.tutorial.userService.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorial.userService.model.User;
import com.tutorial.userService.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> saveUser() {
        return userRepository.findAll();
    }
}
