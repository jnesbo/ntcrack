package com.newbegin.project.newbegin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String username;


    @Async
    public void send(String emailTo, String subject, String message) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(message, true);
        helper.setTo(emailTo);
        helper.setSubject(subject);
        helper.setFrom(username);
        mailSender.send(mimeMessage);

    }

}
