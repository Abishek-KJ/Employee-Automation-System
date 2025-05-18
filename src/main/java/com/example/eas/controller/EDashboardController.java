package com.example.eas.controller;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import com.example.eas.service.EDashboardService;
import com.example.eas.utility.SessionChecker; 

@Controller 
public class EDashboardController { 
	
	@Autowired 
	private EDashboardService eDashboardService; 
	
	@Autowired 
	private SessionChecker sessionChecker; 
	
	@GetMapping("/employees/dashboard") 
	public String getMonthlyLeaveCount(HttpSession session, Model model) { 
		
		System.out.println(sessionChecker.isSessionKeyPresent(session, "employeeName")); 
		
		String employeeName = (String) session.getAttribute("employeeName"); 
		String employeeJobRole = (String) session.getAttribute("employeeJobRole"); 
		
		EDashboardService.DashboardData data = eDashboardService.getDashboardData(employeeName, employeeJobRole); 
		
		
		
		model.addAttribute("totalDays", data.getTotalDays()); 
		model.addAttribute("pendingLeaves", data.getPendingLeaves()); 
		model.addAttribute("todayDate", data.getTodayDate()); 
		model.addAttribute("latestLeaves", data.getLatestLeaves()); 
		return "eDashboard"; 
		
	} 
} 
