package com.example.eas.service; 

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eas.controller.AddEmployeeController;
import com.example.eas.dao.AddEmployeeDAO;
import com.example.eas.dto.EmployeeUpdateForm;
import com.example.eas.entity.AddEmployee;
import com.example.eas.utility.SessionChecker;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;

@Service 
public class AddEmployeeService { 
	
	/* @Autowired 
	private AddEmployeeDAO addEmployeeDAO; 
	
	public void saveEmployee(AddEmployee addEmployee) { 
		addEmployeeDAO.save(addEmployee); 
	} */ 
	@Autowired 
	private AddEmployeeDAO addEmployeeDAO; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@Autowired 
	private HttpSession employeeSession; 
	
	private AddEmployee addEmployee; 
	
	@Autowired 
	@Lazy 
	private AddEmployeeController addEmployeeController; 
		
	@Autowired 
	private SessionChecker sessionChecker; 
	
	
	// public List<AddEmployee> getAllUsers(){ 
		
	// } 
	@Transactional
	public void saveUser(AddEmployee addEmployee) {  
		BigDecimal convertedCtc = addEmployee.getCtc().multiply(BigDecimal.valueOf(100000)); 
		addEmployee.setCtc(convertedCtc); 
		addEmployeeDAO.saveEmployee(addEmployee); 
	} 
	
	public List<AddEmployee> getAllEmployees(){ 
		String managerJobRole = (String) employeeSession.getAttribute("managerJobRole"); 
		String pattern = "%" + managerJobRole.toLowerCase() + "%"; 
		TypedQuery<AddEmployee> query = entityManager.createQuery("SELECT e FROM AddEmployee e WHERE LOWER(e.jobRole) LIKE :jobRolePattern ", AddEmployee.class).setParameter("jobRolePattern", pattern); 
		return query.getResultList(); 
	}   
	
	public boolean verifyEmployee(String mailId, String password) { 
//		System.out.println(email+" "+password);
//		String jpql = "SELECT u.password FROM AddEmployee u WHERE u.mailId = :email"; 
//		String jpql = "SELECT e FROM AddEmployee e WHERE e.email = :email AND e.password = :password";
//		Query query = entityManager.createQuery(jpql); 
//		query.setParameter("email", email); 
//		try { 
//			String storedPassword = (String) query.getSingleResult();
//			System.out.println(storedPassword);
//			return storedPassword.equals(password); 
//		} 
//		catch(Exception exception) { 
//			
//			return false; 
//		} 
		
		 try {
			 	// Employee verification 
		        String query = "SELECT e FROM AddEmployee e WHERE e.mailId = :mailId AND e.password = :password";
		        AddEmployee employee = entityManager.createQuery(query, AddEmployee.class)
		                                            .setParameter("mailId", mailId)
		                                            .setParameter("password", password)
		                                            .getSingleResult(); 
		        
		        
		        // Take name from the employee details and store it in session. 
		        String employeeName = entityManager.createQuery("SELECT e.empName FROM AddEmployee e WHERE e.mailId = :mailId AND e.password = :password", String.class).setParameter("mailId", mailId).setParameter("password", password).getSingleResult(); 
		        String employeeJobRole = entityManager.createQuery("SELECT e.jobRole FROM AddEmployee e WHERE e.mailId = :mailId AND e.password = :password", String.class).setParameter("mailId", mailId).setParameter("password", password).getSingleResult(); 
		        String employeeIdentity = entityManager.createQuery("SELECT e.empCode FROM AddEmployee e WHERE e.mailId = :mailId AND e.password = :password", String.class).setParameter("mailId", mailId).setParameter("password", password).getSingleResult(); 
		        
			     employeeSession.setAttribute("employeeName", employee.getEmpName()); // Employee name added in session 
			     employeeSession.setAttribute("employeeJobRole", employee.getJobRole()); // Employee job role added in session 
			     employeeSession.setAttribute("employeeCode", employee.getEmpCode()); // Employee code added in session 
			     
			     System.out.println("Employee code exists in session : " + addEmployeeController.isSessionKeyPresent(employeeSession, "employeeCode")); 
			     System.out.println("Employee job role from session : " + sessionChecker.isSessionKeyPresent(employeeSession, "employeeJobRole")); 
			     
			     if(employeeSession == null) { 
			    	 System.out.println("Session is empty."); 
			     } 
			     else { 
			    	 System.out.println("Employee name : " + employeeName); 
			    	 System.out.println("Employee name : " + employeeSession.getAttribute("employeeName")); 
			    	 System.out.println("Employee code : " + employeeSession.getAttribute("employeeCode")); 
			     } 
			     
			     // System.out.println("Employee name : " + employeeName); 
			     
			     System.out.println(employeeName + " Job Role is : " + employeeJobRole); 
		        
		        return employee != null; // If employee is found, return true
		    } catch (NoResultException e) {
		        return false; // No employee found with given email and password
		    } 
		  
	} 
	
	
	public AddEmployee getEmployeeByCode(String empCode) { 
		try { 
			String query = "SELECT e FROM AddEmployee e WHERE e.empCode = :empCode"; 
			return entityManager.createQuery(query, AddEmployee.class)
								.setParameter("empCode", empCode) 
								.getSingleResult(); 
		} 
		catch(NoResultException exception) { 
			return null; 
		} 
	} 
	
	// Update employee by manager 
	@Transactional 
	public void updateEmployeeByManager(EmployeeUpdateForm employeeUpdateForm) { 
	   	List<AddEmployee> results = entityManager.createQuery("SELECT e FROM AddEmployee e WHERE e.empCode = :empCode", AddEmployee.class)
				.setParameter("empCode", employeeUpdateForm.getEmpCode())
				.getResultList(); 
	   	if(results == null || results.isEmpty()) { 
	   		throw new EntityNotFoundException("Employee not found with employee code : " + employeeUpdateForm.getEmpCode()); 
	   	} 

	   	AddEmployee existingEmployee = results.get(0); 
		
		
		if(employeeUpdateForm.getEmpName() != null && !employeeUpdateForm.getEmpName().isBlank()) { 
			existingEmployee.setEmpName(employeeUpdateForm.getEmpName()); 
		} 
		
		if(employeeUpdateForm.getJobRole() != null && !employeeUpdateForm.getJobRole().isBlank()) { 
			existingEmployee.setJobRole(employeeUpdateForm.getJobRole()); 
		} 
		
		if(employeeUpdateForm.getJoinDate() != null) { 
			existingEmployee.setJoinDate(employeeUpdateForm.getJoinDate()); 
		} 
		
		System.out.println(existingEmployee.getJoinDate()); 
		System.out.println(employeeUpdateForm.getJoinDate()); 
		
		// Update employee code if provided and unique 
		
		if(employeeUpdateForm.getEmpCode() != null && !employeeUpdateForm.getEmpCode().isBlank() && !employeeUpdateForm.getEmpCode().equals(existingEmployee.getEmpCode())) { 
			// existingEmployee.setEmpCode(employeeUpdateForm.getEmpCode()); 
			Long codeCount = entityManager.createQuery("SELECT COUNT(e) FROM AddEmployee e WHERE e.empCode = :newCode", Long.class)
									.setParameter("newCode", employeeUpdateForm.getNewEmpCode())
									.getSingleResult(); 
			
			if(codeCount > 0) { 
				throw new IllegalArgumentException("New employee code : '" + employeeUpdateForm.getNewEmpCode() + "' is already in use."); 			
			} 
			existingEmployee.setEmpCode(employeeUpdateForm.getNewEmpCode()); 
		} 
		
		entityManager.merge(existingEmployee); 
		
	} 
		
	// Delete employee by manager 
	
	@Transactional 
	public void deleteEmployeeByCode(String employeeCode) { 
		/* AddEmployee employee = entityManager.find(AddEmployee.class, employeeCode); 
		if(employee != null) { 
			entityManager.remove(employee); 		
		} 
		else { 
			throw new IllegalArgumentException("Employee not found : " + employeeCode); 
		} */ 
		
		if(employeeCode == null || employeeCode.trim().isEmpty()) { 
			throw new IllegalArgumentException("Employee code must not be null or empty"); 
		} 
		
		String jpql = "DELETE FROM AddEmployee e WHERE e.empCode = :empCode"; 
		int rowsAffected = entityManager.createQuery(jpql).setParameter("empCode", employeeCode).executeUpdate(); 
		
		if(rowsAffected == 0) { 
			throw new RuntimeException("No employee found with code : " + employeeCode); 
		} 
		
		System.out.println("Employee with code : " + employeeCode + " deleted successfully!"); 
	} 
	
} 


