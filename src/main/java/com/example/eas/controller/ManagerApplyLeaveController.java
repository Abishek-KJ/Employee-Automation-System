package com.example.eas.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eas.entity.ManagerApplyLeave;
import com.example.eas.service.EmailService;
import com.example.eas.service.ManagerApplyLeaveService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/manager") 
public class ManagerApplyLeaveController { 
	
	
	private final EmailService emailService; 
	
	private HttpSession httpSession; 
		
	private final ManagerApplyLeaveService managerApplyLeaveService; 
	
	public ManagerApplyLeaveController(EmailService emailService, ManagerApplyLeaveService managerApplyLeaveService) { 
		this.emailService = emailService; 
		this.managerApplyLeaveService = managerApplyLeaveService;
	} 
	
	@PostMapping("/apply-leave") 
	public String managerApplyLeave(@ModelAttribute ManagerApplyLeave managerApplyLeave, HttpSession session) throws MessagingException { 
		
		String managerName = (String) session.getAttribute("managerName"); 
		managerApplyLeave.setLeaveManagerName(managerName); 
		
		String managerJobRole = (String) session.getAttribute("managerJobRole"); 
		managerApplyLeave.setTeamName(managerJobRole); 
		
		managerApplyLeaveService.saveManagerApplyLeave(managerApplyLeave); 

		if(managerName != null) { 
			String subject = "New Leave Request From : " + managerName;  
			String takeActionLink = "http://localhost:8080/manager/show#"; 
			String body = "<p>An manager (" + managerName + ") with job role " + managerJobRole + " has applied for leave.</p>" 
							+ "<p>To take action and view more details : <a href = '"+ takeActionLink +"'>Click here</a></p>" ; 
			emailService.sendEmail("kjabishek2003@gmail.com", "employeeautomationsystem@gmail.com", subject, body); 
		} 
		
		return "Mapplyleave"; 
		
		
	}

} 


