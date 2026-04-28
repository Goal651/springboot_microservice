package com.tutorial.authService.producer;

import com.tutorial.authService.dto.AuthEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "auth-events";

    public void sendUserEvent(Long userId, String userName, String userEmail,String eventType) {
        
        AuthEvent event = new AuthEvent(
            eventType,
            userId,
            userName,
            userEmail,
            System.currentTimeMillis()
        );
        kafkaTemplate.send(TOPIC, userId.toString(), event);
        log.info("Sent {} event for user: {}", eventType,userName);
    }

}