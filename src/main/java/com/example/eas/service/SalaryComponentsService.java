package com.example.eas.service; 

import com.example.eas.dto.EmployeeSalaryDTO;
import com.example.eas.dto.ManagerSalaryDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eas.entity.AddEmployee;
import com.example.eas.entity.SalaryComponents;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service 
// @Transactional 
public class SalaryComponentsService { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@Transactional 
	public SalaryComponents saveSalaryComponents(SalaryComponents salaryComponents) { 
		entityManager.persist(salaryComponents); 
		return salaryComponents; 
	} 
	
	public SalaryComponents getSalaryComponentsForCurrentMonth() { 
		int currentMonth = LocalDate.now().getMonthValue(); 
		int currentYear = LocalDate.now().getYear(); 
		String queryString = "SELECT s FROM SalaryComponents s " + "WHERE FUNCTION('MONTH', s.salaryDate) = :month " + "AND FUNCTION('YEAR', s.salaryDate) = :year";  
		TypedQuery<SalaryComponents> query = entityManager.createQuery(queryString, SalaryComponents.class);  
		query.setParameter("month", currentMonth); 
		query.setParameter("year", currentYear); 
		
		List<SalaryComponents> resultList = query.getResultList(); 
		return resultList.isEmpty() ? null : resultList.get(0); 
	} 
	
	 public List<EmployeeSalaryDTO> getAllEmployees(){ 
		 
		 String queryString = "SELECT new com.example.eas.dto.EmployeeSalaryDTO(e.empCode, e.empName, e.jobRole, e.ctc) FROM AddEmployee e"; 
		 TypedQuery<EmployeeSalaryDTO> query = entityManager.createQuery(queryString, EmployeeSalaryDTO.class); 
		 return query.getResultList(); 
	 } 
	 
	 public List<ManagerSalaryDTO> getAllManagers(){ 
		 String queryString = "SELECT new com.example.eas.dto.ManagerSalaryDTO(m.name, m.managerMailId, m.managerCTC) FROM ManagerSignup m"; 
		 TypedQuery<ManagerSalaryDTO> query = entityManager.createQuery(queryString, ManagerSalaryDTO.class); 
		 return query.getResultList(); 
	 } 
	 
		public int getPendingLeaveCountForCurrentMonth(String employeeName) { 
			
			LocalDate now = LocalDate.now(); 
			LocalDate startOfMonth = now.withDayOfMonth(1); 
			LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth()); 
			
			String jpql = "SELECT COUNT(a) FROM ApplyLeave a " + "WHERE a.leaveEmployeeName = :leaveEmployeeName " + "AND a.status = 2 " + "AND a.leaveDate BETWEEN :start AND :end"; 
			
			Long count = entityManager.createQuery(jpql, Long.class)
						.setParameter("leaveEmployeeName", employeeName) 
						.setParameter("start", startOfMonth) 
						.setParameter("end", endOfMonth) 
						.getSingleResult(); 
			
			return count.intValue(); 
		}

} 
