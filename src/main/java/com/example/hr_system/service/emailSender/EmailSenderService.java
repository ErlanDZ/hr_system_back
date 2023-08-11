package com.example.hr_system.service.emailSender;

import com.example.hr_system.entities.FileData;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Objects;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void inviteJobSeeker(String setFrom ,String email, String message){
        SimpleMailMessage inviteMessage = new SimpleMailMessage();
        inviteMessage.setFrom(setFrom);
        inviteMessage.setTo(email);
        inviteMessage.setText(message);
        mailSender.send(inviteMessage);
    }
    public void sendEmail(String toEmail, String subject, String body, int code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("devsfactoryBack@gmail.com");
        message.setTo(toEmail);
        message.setText(body+"\n The refresh code: "+ code);
        message.setSubject(subject);
        mailSender.send(message);
    }

    public void inviteJobSeeker(String setFrom, String email, String message, FileData fileData) throws MessagingException {
//        SimpleMailMessage inviteMessage = new SimpleMailMessage();
//        inviteMessage.setFrom(setFrom);
//        inviteMessage.setTo(email);
//        inviteMessage.
//        inviteMessage.setText(message);
//        mailSender.send(inviteMessage);
        MimeMessage messages = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(messages, true);
        helper.setFrom("devsfactoryback@gmail.com");
        helper.setTo(email);
        helper.setText(setFrom+" is sent you message: "+message);

        Resource resource = null;
        try {
             resource = new UrlResource(fileData.getPath());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        helper.addAttachment(Objects.requireNonNull(fileData.getName()), resource, "application/pdf");
//        for(Resource attachment: fileData)
//            helper.addAttachment(Objects.requireNonNull(attachment.getFilename()), attachment, "application/pdf");

        mailSender.send(messages);
    }
}