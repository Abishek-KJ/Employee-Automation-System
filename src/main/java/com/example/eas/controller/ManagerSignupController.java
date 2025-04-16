package com.example.eas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eas.entity.ManagerSignup;
import com.example.eas.service.ManagerSignupService;
import com.example.eas.utility.SessionChecker;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager") 
public class ManagerSignupController { 
	
	@Autowired 
	private ManagerSignupService managerSignupService; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	private final HttpSession session; 
	
	private String extractJobRole; 
	
	@Autowired 
	private SessionChecker sessionChecker; 
	
	public ManagerSignupController(HttpSession session) { 
		this.session = session; 
	} 
	
	public String getExtractJobRole() {
		return extractJobRole;
	}


	public void setExtractJobRole(String extractJobRole) {
		this.extractJobRole = extractJobRole;
	}

	// Manager signup 
	
	@GetMapping("/show") 
	public String showManagerSignupPage(Model model) { 
		model.addAttribute("managerSignup", new ManagerSignup()); 
		return "welcome"; 
	} 
	
	
	@PostMapping("/save") 
	public String saveManagerCredentials(@ModelAttribute ManagerSignup managerSignup) { 
		managerSignupService.saveManagerSignup(managerSignup); 
		return "redirect:/manager/show"; 
	} 
	
	// Manager login 
	
	@PostMapping("/login-manager") 
	public String loginManager(@RequestParam("managerMailId") String managerMailId, @RequestParam("password") String password, Model model, HttpSession managerSession) {  
		

		// Login verification and success message. 
		if(managerSignupService.verifyManager(managerMailId, password)) { 
			managerSession.setAttribute("managerLoggedInEmail", managerMailId); 
			model.addAttribute("message", "Login Successfull!"); 
			
			
			// Extracting job role from the manager by using their mail address. 
			extractJobRole = (managerMailId == null || !managerMailId.contains(".")) ? "Invalid Email" : managerMailId.split("[.@]")[1]; 
			System.out.println("Manager's job role : " + extractJobRole); 
			session.setAttribute("managerJobRole", extractJobRole); 
			System.out.println("Manager job role : " + getExtractJobRole()); 
			
			
			
			System.out.println("Managaer mail id from session : " + sessionChecker.isSessionKeyPresent(managerSession, "managerLoggedInEmail")); 
			
			
			// Manager's dashboard page. 
			return "mDashboard"; 			
		} 
		else { 
			model.addAttribute("error", "Invalid Email or Password!"); 
			return "redirect:/manager/show"; 
		} 
	} 

} 
