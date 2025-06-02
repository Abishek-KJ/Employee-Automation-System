package com.example.eas.controller; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
	
	private String managerName; 
	
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
			
			if(managerMailId == null || !managerMailId.contains("@")) { 
				return ""; 
			} 
			
			String managerMailAddress = managerMailId.split("@")[0]; 
			
			String[] parts = managerMailAddress.split("\\."); 
			
			if(parts.length <= 1) { 
				return parts[0]; 
			} 
			
			
			StringBuilder nameBuilder = new StringBuilder(); 
			
			for(int i = 0; i < parts.length - 1; i++) { 
				nameBuilder.append(parts[i]); 
				if(i < parts.length - 2) { 
					nameBuilder.append("."); 
				} 
			} 
			
			session.setAttribute("managerName", nameBuilder.toString()); 
			System.out.println("Manager's Name : " + nameBuilder.toString()); 
			// Manager's dashboard page. 
			return "redirect:/manager/dashboard"; 			
		} 
		else { 
			model.addAttribute("error", "Invalid Email or Password!"); 
			return "redirect:/manager/show"; 
		} 
	} 
	
	// Manager change password 
	
			@Transactional 
			@PostMapping("/update-password") 
			public String updatePassword(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session) { 
				
				String mailId = (String) session.getAttribute("managerLoggedInEmail"); 
				System.out.println("Mail identity from session : " + mailId); 
				System.out.println(session.getId()); 
				
				System.out.println("Mail identity exists in session : " + sessionChecker.isSessionKeyPresent(session, "managerLoggedInEmail")); 
				
				// If email is not found in session, redirect to login 
				
				if(mailId == null) { 
					return "Mchangepassword"; 
				} 
				
				try { 
					Query query = entityManager.createQuery("SELECT e.password FROM ManagerSignup e WHERE e.managerMailId = :managerMailId"); 
					query.setParameter("managerMailId", mailId); 
					String storedPassword = (String) query.getSingleResult(); 
					
					if(!storedPassword.equals(currentPassword)) { 
						return "Mchangepassword"; 
					} 
					
					if(!newPassword.equals(confirmPassword)) { 
						return "Mchangepassword"; 
					} 
					
					// Update the password 
					Query updateQuery = entityManager.createQuery("UPDATE ManagerSignup e SET e.password = :newPassword WHERE e.managerMailId = :managerMailId"); 
					updateQuery.setParameter("newPassword", newPassword); 
					updateQuery.setParameter("managerMailId", mailId); 
					updateQuery.executeUpdate(); 
					
					session.invalidate(); 
					
					return "Mchangepassword"; 
				} 
				catch(NoResultException exception) { 
					return "redirect:/change-password?error=User not found"; 
				} 
				
			} 


} 
