package com.tutorial.notificationService.consumer;

import com.tutorial.notificationService.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthEventConsumer {

    @KafkaListener(topics = "auth-events", groupId = "notification-service-group")
    public void consumeUserEvent(UserEvent event) {
        log.info("Received auth event: {}", event);
        System.out.println(event);
    }
}