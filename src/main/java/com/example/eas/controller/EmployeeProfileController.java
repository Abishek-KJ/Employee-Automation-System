package com.example.eas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.eas.entity.AddEmployee;
import com.example.eas.service.EmployeeProfileService;

import jakarta.servlet.http.HttpSession;

@Controller 
public class EmployeeProfileController {
	
	@Autowired 
	private EmployeeProfileService employeeProfileService; 
	
	@GetMapping("/employees/profile") 	
	public String showProfile(HttpSession session, Model model) { 
		
		String employeeCode = (String) session.getAttribute("employeeCode"); 
		
		if(employeeCode != null) { 
			AddEmployee employee = employeeProfileService.getEmployeeByCode(employeeCode); 
			model.addAttribute("employee", employee); 
		} 
		
		return "Eprofile"; 
	} 
	

	// @GetMapping("/employees/update-information") 	
	public String updateInformation(HttpSession session, Model model) { 
		
		String employeeCode = (String) session.getAttribute("employeeCode"); 
		
		if(employeeCode != null) { 
			AddEmployee employee = employeeProfileService.getEmployeeByCode(employeeCode); 
			model.addAttribute("employee", employee); 
		} 
		
		return "Eupdateinformation"; 
	} 

} 



