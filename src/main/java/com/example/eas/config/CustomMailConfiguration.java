package com.example.eas.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/* @Configuration
public class CustomMailConfiguration {
	
	@Bean 
	public JavaMailSender customMailSender() { 
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); 
		mailSender.setHost("smtp.gmail.com"); 
		mailSender.setPort(587); 
		mailSender.setUsername("neoabishek@gmail.com"); 
		mailSender.setPassword("jkrveqhqnmuypbev"); 
		
		Properties properties = mailSender.getJavaMailProperties(); 
		properties.put("mail.smtp.auth", "true"); 
		properties.put("mail.smttp.starttls.enable", true); 
		
		return mailSender; 
	} 

} */ 
