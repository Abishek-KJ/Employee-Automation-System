package com.example.eas.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.eas.entity.ApplyLeave; 


@Service
public class EDashboardService { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public DashboardData getDashboardData(String empName, String jobRole) { 
		
		System.out.println(empName); 
		System.out.println(jobRole); 
		LocalDate now = LocalDate.now(); 
		LocalDate firstDay = now.withDayOfMonth(1); 
		LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth()); 
		
		
		// 1. Fetch all approved leaves for current month 
		String jpql = "SELECT l FROM ApplyLeave l " + "WHERE l.leaveEmployeeName = :empName " + 
					  "AND l.teamName = :teamName " +
					  "AND l.leaveFrom <= :end AND l.leaveTo >= :start";   
		 

		List<ApplyLeave> leaves = entityManager.createQuery(jpql, ApplyLeave.class)
								  .setParameter("empName", empName)
								  .setParameter("teamName", jobRole)
								  .setParameter("start", firstDay)
								  .setParameter("end", lastDay)
								  .getResultList(); 
		
		int totalDays = 0; 
		int approvedLeaves = 0; 
		int approvedLeaveDays = 0; 
		int pendingLeaves = 0; 
		
		
		// 2. Get government holidays within this month 
		List<LocalDate> holidays = getCurrentMonthHolidays(firstDay, lastDay); 
		
		
		for(ApplyLeave leave : leaves) { 
			LocalDate from = leave.getLeaveFrom(); 
			LocalDate to = leave.getLeaveTo(); 
			
			
			//Clip leave range to current month 
			LocalDate start = from.isBefore(firstDay) ? firstDay : from; 
			LocalDate end = to.isAfter(lastDay) ? lastDay : to; 
			/* int days = (int) ChronoUnit.DAYS.between(actualFrom, actualTo) + 1; 
			totalDays = totalDays + days; */ 
			
			
			
			int days = 0; 
			LocalDate current = start; 
			
			while(!current.isAfter(end)) { 
				DayOfWeek day = current.getDayOfWeek(); 
				if(day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY && !holidays.contains(current)) {  
					days = days + 1; 
				} 
				current = current.plusDays(1); 
			} 
			
			if(leave.getStatus() == 1) { 
				approvedLeaves = approvedLeaves + 1; 
				approvedLeaveDays = approvedLeaveDays + days; 
			} 
			else if (leave.getStatus() == 0) { 
				pendingLeaves = pendingLeaves + 1; 
			} 
			else { 
				
			} 
		} 
		
		int lossOfPay = Math.max(0, (approvedLeaveDays - 2) * 100); 
		
		String latestLeavesJpql = "SELECT l FROM ApplyLeave l " + 
								  "WHERE l.leaveEmployeeName = :empName " + 
								  "AND l.teamName = :teamName " + 
								  "ORDER BY l.id DESC"; 
		List<ApplyLeave> latestLeaves = entityManager.createQuery(latestLeavesJpql, ApplyLeave.class)
										.setParameter("empName", empName) 
										.setParameter("teamName", jobRole)
										.setMaxResults(4)
										.getResultList(); 
		// System.out.println(((ApplyLeave) approvedLeaves).getStatus()); 
		System.out.println("Approved leave days : " + approvedLeaveDays); 
		return new DashboardData(approvedLeaveDays, pendingLeaves, lossOfPay, latestLeaves); 
	} 
	
	public List<LocalDate> getCurrentMonthHolidays(LocalDate start, LocalDate end){ 
		String jpql = "SELECT h.holidayDate FROM Holiday h WHERE h.holidayDate BETWEEN :start AND :end"; 
		return entityManager.createQuery(jpql, LocalDate.class) 
				.setParameter("start", start) 
				.setParameter("end", end) 
				.getResultList(); 
	} 
		
	public static class DashboardData{ 
		
		private int totalDays; 
		private int pendingLeaves; 
		private int lossOfPay; 
		private List<ApplyLeave> latestLeaves; 
		
		public DashboardData(int totalDays, int pendingLeaves, int lossOfPay, List<ApplyLeave> latestLeaves) {
			// super();
			this.totalDays = totalDays;
			this.pendingLeaves = pendingLeaves;
			this.lossOfPay = lossOfPay;
			this.latestLeaves = latestLeaves;
		} 
		
		public int getTotalDays() {
			return totalDays;
		}
		public int getPendingLeaves() {
			return pendingLeaves;
		}
		public int getLossOfPay() {
			return lossOfPay;
		}
		public List<ApplyLeave> getLatestLeaves() {
			return latestLeaves;
		} 
		
	} 
} 