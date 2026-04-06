package com.tutorial.notificationService.controller;

import com.tutorial.notificationService.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/test-welcome")
    public ResponseEntity<Map<String, String>> testWelcomeEmail(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String userName = request.get("userName");
            
            if (email == null || userName == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "email and userName are required"));
            }
            
            emailService.sendWelcomeEmail(email, userName);
            log.info("Test welcome email sent to: {}", email);
            
            return ResponseEntity.ok(Map.of(
                "message", "Welcome email sent successfully",
                "email", email,
                "userName", userName
            ));
        } catch (Exception e) {
            log.error("Failed to send test welcome email", e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Failed to send email: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/test-update")
    public ResponseEntity<Map<String, String>> testUpdateEmail(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String userName = request.get("userName");
            
            if (email == null || userName == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "email and userName are required"));
            }
            
            emailService.sendUserUpdateEmail(email, userName);
            log.info("Test update email sent to: {}", email);
            
            return ResponseEntity.ok(Map.of(
                "message", "Update email sent successfully",
                "email", email,
                "userName", userName
            ));
        } catch (Exception e) {
            log.error("Failed to send test update email", e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Failed to send email: " + e.getMessage()
            ));
        }
    }

    @PostMapping("/test-deletion")
    public ResponseEntity<Map<String, String>> testDeletionEmail(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String userName = request.get("userName");
            
            if (email == null || userName == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "email and userName are required"));
            }
            
            emailService.sendUserDeletionEmail(email, userName);
            log.info("Test deletion email sent to: {}", email);
            
            return ResponseEntity.ok(Map.of(
                "message", "Deletion email sent successfully",
                "email", email,
                "userName", userName
            ));
        } catch (Exception e) {
            log.error("Failed to send test deletion email", e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Failed to send email: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "notification-service",
            "message", "Email service is running"
        ));
    }
}
