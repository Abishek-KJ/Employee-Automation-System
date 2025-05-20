package com.example.eas.controller; 

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.eas.entity.ManagerApplyLeave;
import com.example.eas.service.EmailService;
import com.example.eas.service.ManagerApplyLeaveService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/manager") 
public class ManagerApplyLeaveController { 
	
	
	private final EmailService emailService; 
	
	private final HttpSession httpSession; 
		
	private final ManagerApplyLeaveService managerApplyLeaveService; 
	
	public ManagerApplyLeaveController(EmailService emailService, ManagerApplyLeaveService managerApplyLeaveService, HttpSession httpSession) { 
		this.emailService = emailService; 
		this.managerApplyLeaveService = managerApplyLeaveService; 
		this.httpSession = httpSession; 
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
	
	@GetMapping("/all-roles") 
	public String showAllManagers(Model model) { 
		List<ManagerApplyLeave> managerApplyLeave = managerApplyLeaveService.getManagerLeaves(); 
		model.addAttribute("managerApplyLeave", managerApplyLeave); 
		return "Aactionrequired"; 
	} 
	
	@PutMapping("/updateStatus/{leaveId}/{status}") 
	@ResponseBody 
	public String updateStatus(@PathVariable int leaveId, @PathVariable int status) { 
		managerApplyLeaveService.updateLeaveStatus(leaveId, status); 
		return "Leave status updated"; 
	} 
	
	@GetMapping("/action-taken") 
	public String getActionTakenLeaves(Model model) { 
		
		List<ManagerApplyLeave> managerApplyLeave = managerApplyLeaveService.getAllActionTakenLeaves(); 
		model.addAttribute("managerApplyLeave", managerApplyLeave); 
		return "Aactiontaken"; 
	} 
	
	@GetMapping("/leave-history") 
	public String getLeaveHistory(Model model) { 
		// httpSession.getAttribute("managerName"); 
		
		String managerName = (String) httpSession.getAttribute("managerName"); 
		List<ManagerApplyLeave> managerApplyLeave = managerApplyLeaveService.getNameWiseLeaveHistory(managerName); 
		model.addAttribute("managerApplyLeave", managerApplyLeave); 
		return "Mleavehistory"; 
	} 
} 




