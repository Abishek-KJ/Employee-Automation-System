package com.example.eas.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.eas.controller.ApplyLeaveController;
import com.example.eas.dao.ApplyLeaveDAO;
import com.example.eas.entity.ApplyLeave;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class ApplyLeaveService { 
	
	@Autowired
	private ApplyLeaveDAO applyLeaveDAO; 
	
	@PersistenceContext 
	private EntityManager entityManager;  
	
	@Autowired 
	private EmailService emailService; 
	
	@Autowired 
	private HttpSession httpSession; 
	
	private Model model; 
	
	private String getManagerJobRole; 
	
	private String employeeName; 

	private String employeeJobRole; 
	
	
	public void setEmployeeName(String employeeName) {
		this.employeeName = (String) httpSession.getAttribute("employeeName"); 
		model.addAttribute("employeeName", employeeName); 
	}
	
	public String getEmployeeName() { 
		System.out.println(employeeName); 
		return employeeName; 
	} 

	public ApplyLeaveDAO getApplyLeaveDAO() {
		return applyLeaveDAO;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public String getGetManagerJobRole() {
		return getManagerJobRole; 
	}

	public void setGetManagerJobRole(String getManagerJobRole) {
		this.getManagerJobRole = getManagerJobRole;
	}

	@Transactional 
	public void saveApplyLeave(ApplyLeave applyLeave) { 
		applyLeaveDAO.saveApplyLeave(applyLeave); 
	} 
		
	public List<ApplyLeave> getLeaveRequestByTeam(String teamName){ 	
		String leaveRequestQuery = "SELECT l FROM ApplyLeave l WHERE l.teamName = :teamName AND l.status = 0 ORDER BY l.id DESC"; 
		
		TypedQuery<ApplyLeave> queryExecution = entityManager.createQuery(leaveRequestQuery, ApplyLeave.class); 
		queryExecution.setParameter("teamName", teamName); 
		
		return queryExecution.getResultList(); 
	} 
	
	public List<ApplyLeave> getActionTakenLeaveByManager(String teamName){ 
		String leaveRequestQuery = "SELECT l FROM ApplyLeave l WHERE l.teamName = :teamName AND l.status = 1 OR l.teamName = :teamName AND l.status = 2 ORDER BY l.id DESC"; 
		
		System.out.println("Team Name : " + teamName); 
		
		TypedQuery<ApplyLeave> queryExecution = entityManager.createQuery(leaveRequestQuery, ApplyLeave.class); 
		queryExecution.setParameter("teamName", teamName); 
		
		return queryExecution.getResultList(); 
	} 
		
	@Transactional 
	public void updateStatus(Long id, int status) { 
		entityManager.createQuery("UPDATE ApplyLeave l SET l.status = :status WHERE l.id = :id")
		.setParameter("status", status)
		.setParameter("id", id)
		.executeUpdate(); 
	} 
	
	@Transactional 
	public List<ApplyLeave> getLeaveHistory(String leaveEmployeeName, String teamName){ 
		
		String getLeaveHistoryQuery = "SELECT a FROM ApplyLeave a WHERE a.leaveEmployeeName = :leaveEmployeeName AND a.teamName = :teamName ORDER BY a.id DESC"; 
		TypedQuery<ApplyLeave> getLeaveHistoryQueryExecution = entityManager.createQuery(getLeaveHistoryQuery, ApplyLeave.class); 
		
		getLeaveHistoryQueryExecution.setParameter("leaveEmployeeName", leaveEmployeeName); 
		getLeaveHistoryQueryExecution.setParameter("teamName", teamName); 
		httpSession.setAttribute("employeeJobRole", teamName); 
	
		return getLeaveHistoryQueryExecution.getResultList(); 
	} 
	
	public void setEmployeeJobRole(String employeeJobRole) {
		this.employeeJobRole = (String) httpSession.getAttribute("employeeJobRole"); 
	}
	
	public String getEmployeeJobRole() {
		return employeeJobRole;
	}

	
	public String findManagerEmail(String jobRole) { 
		
		String query = "SELECT m.managerMailId FROM ManagerSignup m WHERE m.managerMailId LIKE :managerPattern"; 
		
		Query q = entityManager.createQuery(query); 
		
		q.setParameter("managerPattern", "%." + jobRole + "@gmail.com"); 
		
		try { 
			return (String) q.setMaxResults(1).getSingleResult(); 
		} 
		catch(Exception exception) { 
			return null; 
		} 	
	} 
	
	
	// Manager to employee mail transaction experiment 
	@Transactional 
	public void updateLeaveStatus(Long leaveId, int status) throws MessagingException { 
		
		// Get employee name 
		String employeeName = getEmployeeName(leaveId); 
		
		// Get employee email 
		String employeeEmail = getEmployeeEmail(employeeName); 
		
		String managerMailId = (String) httpSession.getAttribute("managerLoggedInEmail"); 
		
		String statusMessage = (status == 1) ? "Approved" : "Rejected"; 
 		
		emailService.sendEmail("kjabishek2003@gmail.com", "employeeautomationsystem@gmail.com", "Leave Request Update", "Dear " + employeeName + ",\n\nYour leave request has been " + statusMessage + ".\n\nRegards,\nHR Team"); 
		
		System.out.println("Manager mail address : " + managerMailId); 
		
		System.out.println("Email sent to employee : " + employeeEmail); 
		
	} 
	
	public String getEmployeeName(Long leaveEmployeeId) { 
		try { 
			String empName = (String) entityManager.createQuery("SELECT e.empName FROM AddEmployee e WHERE e.id = " + "(SELECT l.leaveEmployeeId FROM ApplyLeave l WHERE  l.leaveEmployeeId = :leaveEmployeeId)")
					.setParameter("leaveEmployeeId", leaveEmployeeId)
					.getSingleResult(); 
			System.out.println("Employee Name : " + empName); 
			return empName; 
		} 
		catch(NoResultException exception) { 
			System.out.println("Employe Name : null"); 
			return null; 
		} 
	
	} 
	
	public String getEmployeeEmail(String employeeName) { 
		try { 
			String employeeEmail = (String) entityManager.createQuery("SELECT e.mailId FROM AddEmployee e WHERE e.empName = :employeeName")
					.setParameter("employeeName", employeeName)
					.getSingleResult(); 
			System.out.println("Employee Email : " + employeeEmail); 
			return employeeEmail; 
		} 
		catch(NoResultException exception) { 
			System.out.println("Employee Email : null"); 
			return null; 
		} 
	} 
	
	
	// System.out.println(getEmployeeName()); 
	
	
	// Experiment ends 
	
	public List<ApplyLeave> getLeaveAnalyst(){ 
		return getLeaveRequestByTeam("Analyst"); 
	} 
	
	public List<ApplyLeave> getLeaveDeveloper(){ 
		return getLeaveRequestByTeam("Developer"); 
	} 
	
	public List<ApplyLeave> getLeaveTester(){ 
		return getLeaveRequestByTeam("Tester"); 
	} 
	
	public List<ApplyLeave> getLeaveDevops(){ 
		return getLeaveRequestByTeam("Devops"); 
	} 
	
	public List<ApplyLeave> getLeaveCloud(){ 
		return getLeaveRequestByTeam("Cloud"); 
	} 
	
	public List<ApplyLeave> getLeaveHR(){ 
		return getLeaveRequestByTeam("HR"); 
	} 
	
	public List<ApplyLeave> getLeaveMarketing(){ 
		return getLeaveRequestByTeam("Marketing"); 
	} 
		
	
	/* @Transactional 
	public void updateStatus(Long id, String status) { 
		entityManager.createQuery("UPDATE ApplyLeave l SET l.status = :status WHERE l.id = :id")
		.setParameter("status",status)
		.setParameter("id", id) 
		.executeUpdate(); 
	} */ 
	
	
	
	
	
	
} 


