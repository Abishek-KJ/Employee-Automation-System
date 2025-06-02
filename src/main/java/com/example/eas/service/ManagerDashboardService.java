package com.example.eas.service; 	

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List; 
import com.example.eas.entity.ManagerApplyLeave; 

import org.springframework.stereotype.Service;

import com.example.eas.service.EDashboardService.DashboardData;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service 
public class ManagerDashboardService {
	
	private final EDashboardService eDashboardService; 
	
	@PersistenceContext 
	private EntityManager entityManager;  
	
	// private final DashboardData dashboardData; 
	
	public ManagerDashboardService(EDashboardService eDashboardService) { 
		this.eDashboardService = eDashboardService; 
	} 
	
	
	// 1st Card 
	
	public int getTodayAbsentCount(String teamName) { 
		
		LocalDate today = LocalDate.now(); 
		DayOfWeek dayOfWeek = today.getDayOfWeek(); 
		if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) { 
			return 0; 
		} 
		
		String holidayJpql = "SELECT COUNT(h) FROM Holiday h WHERE h.holidayDate = :today"; 
		Long holidayCount = entityManager.createQuery(holidayJpql, Long.class)
							.setParameter("today", today) 
							.getSingleResult(); 
		
		if(holidayCount > 0) { 
			return 0; 
		} 
		
		String leaveJpql = "SELECT COUNT(l) FROM ApplyLeave l " + "WHERE l.status = 1 " + "AND l.leaveFrom <= :today AND l.leaveTo >= :today " + "AND l.teamName = :teamName"; 
		
		Long absentCount = entityManager.createQuery(leaveJpql, Long.class) 
							.setParameter("today", today) 
							.setParameter("teamName", teamName) 
							.getSingleResult(); 
		
		return absentCount.intValue(); 
	} 
	
	
	// 2nd Card 
	
	public DashboardData getManagerDashboardData(String managerName, String managerJobRole) { 
		
		LocalDate now = LocalDate.now(); 
		LocalDate firstDay = now.withDayOfMonth(1); 
		LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth()); 
		
		List<LocalDate> holidays = getCurrentMonthHolidays(firstDay, lastDay); 
		
		String jpql = "SELECT l FROM ManagerApplyLeave l " + "WHERE l.leaveManagerName = :leaveManagerName " + "AND l.teamName = :teamName " + "AND l.leaveFrom <= :end AND l.leaveTo >= :start"; 
		
		List<ManagerApplyLeave> leaves = entityManager.createQuery(jpql, ManagerApplyLeave.class) 
				.setParameter("leaveManagerName", managerName) 
				.setParameter("teamName", managerJobRole) 
				.setParameter("start", firstDay) 
				.setParameter("end", lastDay)
				.getResultList(); 
		
		int approvedLeaveDays = 0; 
		int pendingLeaves = 0; 
		
		for(ManagerApplyLeave leave : leaves) { 
			LocalDate from = leave.getLeaveFrom(); 
			LocalDate to = leave.getLeaveTo(); 
			
			LocalDate start = from.isBefore(firstDay) ? firstDay : from; 
			LocalDate end = to.isAfter(lastDay) ? lastDay : to; 
			
			LocalDate current = start; 
			
			int days = 0; 
			
			while(!current.isAfter(end)) { 
				DayOfWeek day = current.getDayOfWeek(); 
				if(day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY && !holidays.contains(current)) { 
					days++; 
				} 
				current = current.plusDays(1); 
			} 
			
			if(leave.getStatus() == 1) { 
				approvedLeaveDays += days; 
			} 
			else if(leave.getStatus() == 0) { 
				pendingLeaves++; 
			} 
		} 
		return new DashboardData(approvedLeaveDays); 
	} 
	
	public List<LocalDate> getCurrentMonthHolidays(LocalDate start, LocalDate end){ 
		String jpql = "SELECT h.holidayDate FROM Holiday h WHERE h.holidayDate BETWEEN :start AND :end"; 
		return entityManager.createQuery(jpql, LocalDate.class)
				.setParameter("start", start) 
				.setParameter("end", end)
				.getResultList(); 
	}

	public static class DashboardData { 
		
		private int approvedLeaveDays; 
		private int pendingLeaves; 
		
		public DashboardData(int approvedLeaveDays) { 
			this.approvedLeaveDays = approvedLeaveDays; 
		} 
		
		public int getApprovedLeaveDays() { 
			return approvedLeaveDays; 
		}

		public int getPendingLeaves() {
			return pendingLeaves;
		} 
		
	}
	
	// Pending leaves count (3rd card) 
	public int getCountOfPendingLeaves(String managerName, String teamName) { 
	
		return entityManager.createQuery("SELECT COUNT(l) FROM ManagerApplyLeave l " + "WHERE l.teamName = :teamName " + "AND l.leaveManagerName = :leaveManagerName " + "AND status = 0", Long.class)
				.setParameter("teamName" , teamName)
				.setParameter("leaveManagerName", managerName)
				.getSingleResult().intValue(); 
	} 
	
	// Today date (4th card) 
	public LocalDate getTodayDate() { 
		return LocalDate.now(); 
	} 
	
	// Table 
	public List<ManagerApplyLeave> getLastFourLeaves(String managerName, String teamName){ 
		
		return entityManager.createQuery("SELECT l FROM ManagerApplyLeave l " + "WHERE l.teamName = :teamName " + "AND l.leaveManagerName = :leaveManagerName " + "ORDER BY l.id DESC", ManagerApplyLeave.class)
				.setParameter("teamName", teamName)
				.setParameter("leaveManagerName", managerName)
				.setMaxResults(4)
				.getResultList(); 
		
	}
	
	
}

