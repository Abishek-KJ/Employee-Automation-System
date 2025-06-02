package com.example.eas.service; 

import com.example.eas.entity.ManagerApplyLeave;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List; 

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service 
public class AdministratorDashboardService { 
	
	private final ManagerDashboardService managerDashboardService; 
	
	
	@PersistenceContext 
	private EntityManager entityManager; 

	
	public AdministratorDashboardService(ManagerDashboardService managerDashboardService) { 
		this.managerDashboardService = managerDashboardService; 
	}  
	
	// 1st Card 
	
	public int getTodayAbsentCountOfEmployee() { 
			
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
			
			String leaveJpql = "SELECT COUNT(l) FROM ApplyLeave l " + "WHERE l.status = 1 " + "AND l.leaveFrom <= :today AND l.leaveTo >= :today"; 
			
			Long absentCount = entityManager.createQuery(leaveJpql, Long.class) 
								.setParameter("today", today) 
								.getSingleResult(); 
			
			return absentCount.intValue(); 
		} 
		
		public int getTodayAbsentCountOfManager() { 
			
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
			
			String leaveJpql = "SELECT COUNT(l) FROM ManagerApplyLeave l " + "WHERE l.status = 1 " + "AND l.leaveFrom <= :today AND l.leaveTo >= :today"; 
			
			Long absentCount = entityManager.createQuery(leaveJpql, Long.class) 
								.setParameter("today", today) 
								.getSingleResult(); 
			
			return absentCount.intValue(); 
		} 

		
	public LocalDate getLastDateOfMonth() { 
		YearMonth currentYearMonth = YearMonth.now(); 
		LocalDate lastDate = currentYearMonth.atEndOfMonth(); 
		return lastDate; 
	} 
	
	// Pending leaves count (3rd card) 
		public int getCountOfPendingLeaves() { 
		
			return entityManager.createQuery("SELECT COUNT(l) FROM ManagerApplyLeave l WHERE status = 0", Long.class)
					.getSingleResult().intValue(); 
		} 

	// 4th Card 
	
	public LocalDate getTodayDate() { 
		// model.addAttribute(LocalDate.now(), "todayDate"); 
		return LocalDate.now(); 
	} 
	
	// Table 
		public List<ManagerApplyLeave> getLastFourLeaves(){ 
			
			return entityManager.createQuery("SELECT l FROM ManagerApplyLeave l ORDER BY l.id DESC", ManagerApplyLeave.class)
					.setMaxResults(4)
					.getResultList(); 
			
		}
		
}

