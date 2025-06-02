package com.example.eas.controller; 

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eas.entity.ManagerApplyLeave;
import com.example.eas.service.AdministratorDashboardService;

@Controller 
@RequestMapping("/administrator") 
public class AdministratorDashboardController { 
	
	private final AdministratorDashboardService administratorDashboardService; 
	
	public AdministratorDashboardController(AdministratorDashboardService administratorDashboardService) { 
		this.administratorDashboardService = administratorDashboardService; 
	} 
	
	
	@GetMapping("/dashboard") 
	public String getDashboardData(Model model) { 
		
		// List<ManagerApplyLeave> lastFourLeaves = administratorDashboardService.getLastFourLeaves(); 
		// model.addAttribute("lastFourLeaves", lastFourLeaves); 
		
		int countOfTodayAbsentees = administratorDashboardService.getTodayAbsentCountOfEmployee();  
		model.addAttribute("countOfTodayAbsentees", countOfTodayAbsentees); 
		
		int countOfTodayAbsenteesManagers = administratorDashboardService.getTodayAbsentCountOfManager(); 
		model.addAttribute("countOfTodayAbsenteesManagers", countOfTodayAbsenteesManagers); 
		
		LocalDate lastDateOfMonth = administratorDashboardService.getLastDateOfMonth(); 
		model.addAttribute("lastDateOfMonth", lastDateOfMonth); 
		
		int countOfPendingLeaves = administratorDashboardService.getCountOfPendingLeaves(); 
		model.addAttribute("countOfPendingLeaves", countOfPendingLeaves); 
		
		LocalDate todayDate = administratorDashboardService.getTodayDate(); 
		model.addAttribute("todayDate", todayDate); 
		
		List<ManagerApplyLeave> latestFourLeaves = administratorDashboardService.getLastFourLeaves(); 
		model.addAttribute("latestFourLeaves", latestFourLeaves); 
		
		return "aDashboard" ; 
	} 
	
	

} 


