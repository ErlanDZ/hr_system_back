package com.example.hr_system.service.emailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String toEmail, String subject, String body, int code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("devsfactoryBack@gmail.com");
        message.setTo(toEmail);
        message.setText(body+"\n The refresh code: "+ code);
        message.setSubject(subject);
        mailSender.send(message);
    }}