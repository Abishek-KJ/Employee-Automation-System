package com.example.eas.service; 

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eas.dao.ManagerApplyLeaveDAO;
import com.example.eas.entity.ManagerApplyLeave;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service 
public class ManagerApplyLeaveService { 
	
	private final EmailService emailService; 
	private final HttpSession httpSession; 
	private final ManagerApplyLeaveDAO managerApplyLeaveDao; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public ManagerApplyLeaveService(EmailService emailService, HttpSession httpSession, ManagerApplyLeaveDAO managerApplyLeaveDao) { 
		this.emailService = emailService; 
		this.httpSession = httpSession; 
		this.managerApplyLeaveDao = managerApplyLeaveDao; 
	} 
	
	
	@Transactional 
	public void saveManagerApplyLeave(ManagerApplyLeave managerApplyLeave) { 
		managerApplyLeaveDao.saveManagerApplyLeave(managerApplyLeave); 
	}  
	
	public List<ManagerApplyLeave> getManagerLeaves(){ 
		String leaveRequestQuery = "SELECT l FROM ManagerApplyLeave l WHERE l.status = 0"; 
		TypedQuery<ManagerApplyLeave> queryExecution = entityManager.createQuery(leaveRequestQuery, ManagerApplyLeave.class); 
		
		return queryExecution.getResultList(); 
	} 
	
	
	@Transactional 
	public void updateLeaveStatus(int leaveId, int status) { 
		
		
		ManagerApplyLeave managerApplyLeave = entityManager.find(ManagerApplyLeave.class, leaveId); 
		if(managerApplyLeave != null) { 
			managerApplyLeave.setStatus(status); 
			entityManager.merge(managerApplyLeave); 
			
			try { 
				emailService.sendEmail("kjabishek2003@gmail.com", "employeeautomationsystem@gmail.com", "Leave request : " + (status == 1 ? "Approved" : "Rejected"), "Your leave from " + managerApplyLeave.getLeaveFrom() + " to " + managerApplyLeave.getLeaveTo() + " has been " + (status == 1 ? "Approved." : "Rejected.") + "<br>" + "<br> Best regards : " + "<br>" + "<br> Administrator Name"); 
			} 
			catch(Exception exception) { 
				exception.printStackTrace(); 
			} 
			
		} 
		
	} 
	
	@Transactional 
	public List<ManagerApplyLeave> getAllActionTakenLeaves(){ 
		String leaveRequestQuery = "SELECT l FROM ManagerApplyLeave l WHERE l.status > 0"; 
		TypedQuery<ManagerApplyLeave> queryExecution = entityManager.createQuery(leaveRequestQuery, ManagerApplyLeave.class); 
		return queryExecution.getResultList(); 
	} 
	
	@Transactional 
	public List<ManagerApplyLeave> getNameWiseLeaveHistory(String managerName) { 
		String leaveRequestQuery = "SELECT l FROM ManagerApplyLeave l WHERE l.leaveManagerName = :leaveManagerName"; 
		TypedQuery<ManagerApplyLeave> queryExecution = entityManager.createQuery(leaveRequestQuery, ManagerApplyLeave.class); 
		queryExecution.setParameter("leaveManagerName", managerName); 
		return queryExecution.getResultList(); 
	} 

}


