package com.tutorial.notificationService.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Welcome to Our Service!");

            Context context = new Context();
            context.setVariable("userName", userName);
            context.setVariable("toEmail", toEmail);

            String htmlContent = templateEngine.process("welcome-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Welcome email sent successfully to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send welcome email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }

    public void sendUserUpdateEmail(String toEmail, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Your Profile Has Been Updated");

            Context context = new Context();
            context.setVariable("userName", userName);
            context.setVariable("toEmail", toEmail);

            String htmlContent = templateEngine.process("user-update-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("User update email sent successfully to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send user update email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send user update email", e);
        }
    }

    public void sendUserDeletionEmail(String toEmail, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Account Deletion Confirmation");

            Context context = new Context();
            context.setVariable("userName", userName);
            context.setVariable("toEmail", toEmail);

            String htmlContent = templateEngine.process("user-deletion-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("User deletion email sent successfully to: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send user deletion email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send user deletion email", e);
        }
    }
}
