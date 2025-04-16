package com.example.eas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eas.dto.EmployeeUpdateForm;
import com.example.eas.entity.AddEmployee;
import com.example.eas.service.AddEmployeeService;
import com.example.eas.service.UpdateEmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/employees") 
public class UpdateEmployeeController { 
	
	private final UpdateEmployeeService updateEmployeeService; 
	private final AddEmployeeService addEmployeeService; 

	
	public UpdateEmployeeController(UpdateEmployeeService updateEmployeeService, AddEmployeeService addEmployeeService) { 
		this.updateEmployeeService = updateEmployeeService; 
		this.addEmployeeService = addEmployeeService; 
	} 
	
	
	
	@GetMapping("/update-information") 
	public String showUpdateForm(HttpSession session, Model model) { 
		String employeeCode = (String) session.getAttribute("employeeCode"); 
		System.out.println(employeeCode); 
		if(employeeCode != null) { 
			AddEmployee employee = addEmployeeService.getEmployeeByCode(employeeCode); 
			
			EmployeeUpdateForm form = new EmployeeUpdateForm(); 
			
			form.setAddress(employee.getAddress()); 
			form.setCity(employee.getCity()); 
			form.setCountry(employee.getCountry()); 
			form.setGender(employee.getGender()); 
			form.setDob(employee.getDob());  
			System.out.println(employee.getDob()); 
			form.setMobile(employee.getMobile()); 
			
			model.addAttribute("employee", employee); 

		} 
		
		return "Eupdateinformation"; 
	
	} 
	
	
	@PostMapping("/update-information") 
	public String updateEmployeeInformation(@ModelAttribute EmployeeUpdateForm form, HttpSession session) { 
		String employeeCode = (String) session.getAttribute("employeeCode"); 
		if(employeeCode != null) { 
			updateEmployeeService.updateInformation(employeeCode, form); 
		} 
		return "redirect:/employees/profile";  
	} 
	
} 
