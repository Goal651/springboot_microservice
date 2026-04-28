package com.tutorial.mailService.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tutorial.mailService.dto.AuthEvent;

@Service
public class KafkaListenerService {
    @KafkaListener(topics = "auth-events", groupId = "mail-group-id")
    public void processAuthMessages(AuthEvent message){
       

    }
    
}
