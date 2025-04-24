package com.example.eas.controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.example.eas.entity.AddEmployee;
import com.example.eas.service.AddEmployeeService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/employees") 
public class AddEmployeeController { 
	
	@Autowired 
	private AddEmployeeService addEmployeeService; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	
	// Employee add 
	
	@GetMapping("/add") 
	public String showForm(Model model) { 
		model.addAttribute("addEmployee", new AddEmployee()); 
		return "Maddemployee"; 
	} 
	
	@PostMapping("/save") 
	public String saveEmployee(@ModelAttribute AddEmployee addEmployee) { 
		addEmployeeService.saveUser(addEmployee); 
		return "redirect:/employees/add"; 
	} 
	
	@GetMapping("/list") 
	public String showEmployeeList(Model model) { 
		List<AddEmployee> employees = addEmployeeService.getAllEmployees(); 
		model.addAttribute("employees", employees); 
		return "Mmanageemployee"; 
	} 
	
	// Employee login 
	
	@PostMapping("/login-employee") 
	public String loginEmployee(@RequestParam("mailId") String mailId, @RequestParam("password") String password, Model model, HttpSession session) { 
		
		System.out.println("MailId : " + mailId); 
		System.out.println("Password : " + password); 
		
		if(addEmployeeService.verifyEmployee(mailId,  password)) { 
			session.setAttribute("loggedInEmail", mailId); 
			model.addAttribute("message", "Login Successfull!"); 
			
			System.out.println("Employee mail id from session : " + isSessionKeyPresent(session, "loggedInEmail")); 
			return "redirect:/employees/dashboard"; 
		} 
		else { 
			model.addAttribute("error", "Invalid Email or Password!"); 
			return "redirect:/manager/show"; 
		} 	
	} 
	
	// Employee change password 
	
	@Transactional 
	@PostMapping("/update-password") 
	public String updatePassword(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, HttpSession session) { 
		
		String mailId = (String) session.getAttribute("loggedInEmail"); 
		System.out.println("Mail identity from session : " + mailId); 
		System.out.println(session.getId()); 
		
		System.out.println("Mail identity exists in session : " + isSessionKeyPresent(session, "loggedInEmail")); 
		
		// If email is not found in session, redirect to login 
		
		if(mailId == null) { 
			return "Echangepassword"; 
		} 
		
		try { 
			Query query = entityManager.createQuery("SELECT e.password FROM AddEmployee e WHERE e.mailId = :mailId"); 
			query.setParameter("mailId", mailId); 
			String storedPassword = (String) query.getSingleResult(); 
			
			if(!storedPassword.equals(currentPassword)) { 
				return "Echangepassword"; 
			} 
			
			if(!newPassword.equals(confirmPassword)) { 
				return "Echangepassword"; 
			} 
			
			// Update the password 
			Query updateQuery = entityManager.createQuery("UPDATE AddEmployee e SET e.password = :newPassword WHERE e.mailId = :mailId"); 
			updateQuery.setParameter("newPassword", newPassword); 
			updateQuery.setParameter("mailId", mailId); 
			updateQuery.executeUpdate(); 
			
			session.invalidate(); 
			
			return "Echangepassword"; 
		} 
		catch(NoResultException exception) { 
			return "redirect:/change-password?error=User not found"; 
		} 
		
	} 
	
	// Check session values by using this method. 
	
	public boolean isSessionKeyPresent(HttpSession session, String searchKey) { 
		
		Enumeration<String> sessionKeys = session.getAttributeNames(); 
		
		while(sessionKeys.hasMoreElements()) { 
			String key = sessionKeys.nextElement(); 
			if(key.equals(searchKey)) { 
				System.out.println("Found session key : " + key + " with value: " + session.getAttribute(key)); 
				return true; 
			} 
		} 
		System.out.println("Session key : '" + searchKey + "' not found."); 
		return false; 		
	} 
	
	
	// Edit employee by manager 
	@GetMapping("/edit-information/{empCode}") 
	public String editEmployeeForm(@PathVariable String empCode, Model model) { 
		AddEmployee employee = addEmployeeService.getEmployeeByCode(empCode); 
		model.addAttribute("employee", employee); 
		return "Meditemployee"; 
	} 
	
	// Delete employee by manager 
		
	@DeleteMapping("/delete-employee/{empCode}") 
	public ResponseEntity<String> deleteEmployee(@PathVariable String empCode){ 
		try { 
			addEmployeeService.deleteEmployeeByCode(empCode); 
			return ResponseEntity.ok("Employee deleted successfully."); 
		} 
		catch(Exception e) { 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete employee."); 
		} 
	} 
} 
