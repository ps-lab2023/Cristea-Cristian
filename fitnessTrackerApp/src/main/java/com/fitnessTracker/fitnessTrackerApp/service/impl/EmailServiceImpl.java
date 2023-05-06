package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fitnesstrackerapp2@gmail.com");
        message.setTo(to);
        message.setText("Your verification code is " + code + ".");
        message.setSubject("Verification code");
        mailSender.send(message);
    }
}
