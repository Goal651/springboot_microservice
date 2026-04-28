package com.tutorial.mailService.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private static final String SENDER_NAME = "Wigothehacker <bugiriwilson651@gmail.com>";


    public void sendSimpleEmail(String to, String subject, String messageContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(SENDER_NAME);
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlTemplate = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px; background-color: #f9f9f9;\">"
                    +
                    "<div style=\"text-align: center; padding-bottom: 20px; border-bottom: 2px solid #4CAF50;\">" +
                    "<h2 style=\"color: #2c3e50; margin: 0;\">Wigothehacker</h2>" +
                    "</div>" +
                    "<div style=\"color: #444444; font-size: 16px; line-height: 1.6; padding: 20px 0;\">" +
                    "<p>%s</p>" +
                    "</div>" +
                    "<div style=\"text-align: center; padding-top: 20px; font-size: 12px; color: #888888; border-top: 1px solid #e0e0e0;\">"
                    +
                    "<p>&copy; Wigothehacker. All rights reserved.</p>" +
                    "<p>This is an automated message, please do not reply.</p>" +
                    "</div>" +
                    "</div>";

            String finalHtmlBody = String.format(htmlTemplate, messageContent);

            helper.setText(finalHtmlBody, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }
}