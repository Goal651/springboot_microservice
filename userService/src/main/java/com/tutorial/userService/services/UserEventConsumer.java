package com.tutorial.userService.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tutorial.userService.dto.UserEvent;

@Service
public class UserEventConsumer {

    @KafkaListener(topics = "auth-events", groupId = "user-service-group")
    public void consumeUserEvent(UserEvent event) {
       System.out.println("Received user event faaaaaaaaaa: " + event);
    }
}