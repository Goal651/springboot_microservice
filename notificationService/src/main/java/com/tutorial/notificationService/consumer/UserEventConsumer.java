package com.tutorial.notificationService.consumer;

import com.tutorial.notificationService.dto.UserEvent;
import com.tutorial.notificationService.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-service-group")
    public void consumeUserEvent(UserEvent event) {
        log.info("Received user event: {}", event);
        
        try {
            // Process the event based on type
            switch (event.getEventType()) {
                case "USER_CREATED":
                    log.info("Processing USER_CREATED event for user: {}", event.getUserName());
                    emailService.sendWelcomeEmail(event.getUserEmail(), event.getUserName());
                    break;
                case "USER_UPDATED":
                    log.info("Processing USER_UPDATED event for user: {}", event.getUserName());
                    emailService.sendUserUpdateEmail(event.getUserEmail(), event.getUserName());
                    break;
                case "USER_DELETED":
                    log.info("Processing USER_DELETED event for user ID: {}", event.getUserId());
                    // For deleted users, we might not have email, so skip if null
                    if (event.getUserEmail() != null) {
                        emailService.sendUserDeletionEmail(event.getUserEmail(), event.getUserName());
                    }
                    break;
                default:
                    log.warn("Unknown event type: {}", event.getEventType());
            }
        } catch (Exception e) {
            log.error("Error processing user event: {}", event, e);
            // In a production environment, you might want to implement retry logic
            // or send this to a dead-letter queue
        }
    }
}