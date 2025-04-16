package com.example.eas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	
	@Autowired 
	private JavaMailSender mailSender; 
	
	
	public void sendEmail(String to, String from, String subject, String body) throws MessagingException { 
		System.out.println("Mail transaction starts."); 
		System.out.println("Mail from : " + from); 
		System.out.println("Mail to : " + to); 
		MimeMessage message = mailSender.createMimeMessage(); 
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); 
		helper.setTo(to); 
		helper.setFrom(from); 
		helper.setSubject(subject); 
		helper.setText(body, true); // Enable HTML content 
		
		mailSender.send(message); 		
		System.out.println("Mail sent."); 
	}
	
}
