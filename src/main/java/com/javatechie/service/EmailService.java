package com.javatechie.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


	 private final JavaMailSender mailSender;

	    @Autowired
	    public EmailService(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }

  

    public void sendRegistrationEmail(String recipientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Welcome to our Pharmacy management system ");
        message.setText("Thank you for registering. Hope you have a good experience,Feel Free to Use us Again");

        mailSender.send(message);
    }
}
