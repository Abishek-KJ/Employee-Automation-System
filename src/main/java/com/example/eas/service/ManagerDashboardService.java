package com.example.eas.service; 

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.eas.service.EDashboardService.DashboardData;

@Service 
public class ManagerDashboardService {
	
	private final EDashboardService eDashboardService; 
	
	// private final DashboardData dashboardData; 
	
	public ManagerDashboardService(EDashboardService eDashboardService) { 
		this.eDashboardService = eDashboardService; 
	} 
	
	public LocalDate getTodayDate() { 
		return LocalDate.now(); 
	} 
	
	
	
}
