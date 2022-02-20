package com.example.cmscart.models;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Emailservice {

  @Autowired
  JavaMailSender emailSender;
  
  public void sendSimpleMessage(String to, String subject, String text) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);
    mimeMessageHelper.setFrom("190031212cse@gmail.com");
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(text);
    emailSender.send(message);
    System.out.println("email sent");
  }
    
}
