package com.example.eas.controller; 

import java.time.LocalDate;

import org.springframework.stereotype.Controller;

import com.example.eas.service.ManagerDashboardService;

@Controller 
public class ManagerDashboardController {
	
	private final ManagerDashboardService managerDashboardService; 
	
	public ManagerDashboardController(ManagerDashboardService managerDashboardService) { 
		this.managerDashboardService = managerDashboardService; 
	}  
	
	public String getDashboardDate(String managerName) { 
		
		
		
		// 4th Card 
		LocalDate today = managerDashboardService.getTodayDate(); 
		
		return "mDashboard"; 
	}
}
