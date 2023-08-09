package com.example.hr_system.service.emailSender;

import com.example.hr_system.entities.FileData;
import com.nimbusds.jose.util.Resource;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
        FileDataInputStreamSourceAdapter inputStreamSource = new FileDataInputStreamSourceAdapter(fileData);
        helper.addAttachment("file.txt", inputStreamSource);

//        for(Resource attachment: fileData)
//            helper.addAttachment(Objects.requireNonNull(attachment.getFilename()), attachment, "application/pdf");

        mailSender.send(messages);
    }
}