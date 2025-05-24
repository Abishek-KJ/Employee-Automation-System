package com.example.eas.controller; 

import java.time.LocalDate;
import java.util.List; 

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eas.entity.ManagerApplyLeave; 
import com.example.eas.service.ManagerDashboardService;
import com.example.eas.service.ManagerDashboardService.DashboardData;

import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/manager") 
public class ManagerDashboardController {
	
	private final ManagerDashboardService managerDashboardService; 
	
	private final HttpSession httpSession; 
	
	public ManagerDashboardController(ManagerDashboardService managerDashboardService, HttpSession httpSession) { 
		this.managerDashboardService = managerDashboardService; 
		this.httpSession = httpSession; 
	}  
	
	@GetMapping("/dashboard") 
	public String getDashboardDate(String managerName, Model model) { 
		
		String leaveManagerName = (String) httpSession.getAttribute("managerName"); 
		String teamName = (String) httpSession.getAttribute("managerJobRole"); 
		
		int countOfAbsentees = managerDashboardService.getTodayAbsentCount(teamName); 
		model.addAttribute("countOfAbsentees", countOfAbsentees); 
		
		// ManagerDashboardService.DashboardData data = managerDashboardService.getManagerDashboardData(leaveManagerName, teamName); 
		DashboardData data = managerDashboardService.getManagerDashboardData(leaveManagerName, teamName); 
		model.addAttribute("thisMonthLeaves", data.getApprovedLeaveDays()); 
		
		// 3rd Card 
		
		int pendingLeaveRequestCount = managerDashboardService.getCountOfPendingLeaves(leaveManagerName, teamName); 
		model.addAttribute("pendingLeaveRequestCount", pendingLeaveRequestCount); 

		// 4th Card 
		LocalDate today = managerDashboardService.getTodayDate(); 
		model.addAttribute("todayDate", today); 
		
		// Table 
		
		// String managerName = (String) httpSession.getAttribute("managerName"); 
		
		List<ManagerApplyLeave> lastFourLeaves = managerDashboardService.getLastFourLeaves(leaveManagerName, teamName); 
		model.addAttribute("lastFourLeaves", lastFourLeaves); 
		return "mDashboard"; 
	}
}
