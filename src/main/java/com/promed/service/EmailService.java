package com.promed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOrderPlacedMail(String orderSummary) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("ardeep24@gmail.com");
            message.setSubject("New Medicine Order Received");
            message.setText(orderSummary);
            mailSender.send(message);
        } catch (Exception ex) {
            log.info("Mail not sent due to missing SMTP setup. Order details: {}", orderSummary);
        }
    }
}

