package com.example.eas.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eas.entity.SalaryComponents;
import com.example.eas.service.SalaryComponentsService;

@Controller 
@RequestMapping("/create-salary") 
public class SalaryComponentsController { 
		
	private final SalaryComponentsService salaryComponentsService; 
	
	public SalaryComponentsController(SalaryComponentsService salaryComponentsService) { 
		this.salaryComponentsService = salaryComponentsService; 
	} 
	
	@GetMapping("/components") 
	public String showSalaryComponentsForm(Model model) { 
		model.addAttribute("salaryComponents", new SalaryComponents()); 
		return "Asalary"; 
	} 
	
	@PostMapping("/components") 
	public String saveSalaryDetails(@ModelAttribute SalaryComponents salaryComponents) { 
		
		LocalDate today = LocalDate.now(); 
		String salaryPeriod = today.getMonth().name().substring(0, 1).toUpperCase() + 
							  today.getMonth().name().substring(1).toLowerCase() + 
							  ", " + today.getYear(); 
		
		salaryComponents.setSalaryDate(LocalDate.now()); 
		salaryComponentsService.saveSalaryComponents(salaryComponents); 
		return "Aselectbank"; 
		
	} 
	
} 

