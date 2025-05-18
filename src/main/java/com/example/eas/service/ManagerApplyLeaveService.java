package com.example.eas.service; 

import org.springframework.stereotype.Service;

import com.example.eas.dao.ManagerApplyLeaveDAO;
import com.example.eas.entity.ManagerApplyLeave;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service 
public class ManagerApplyLeaveService { 
	
	private final EmailService emailService; 
	private final HttpSession httpSession; 
	private final ManagerApplyLeaveDAO managerApplyLeaveDao; 
	
	public ManagerApplyLeaveService(EmailService emailService, HttpSession httpSession, ManagerApplyLeaveDAO managerApplyLeaveDao) { 
		this.emailService = emailService; 
		this.httpSession = httpSession; 
		this.managerApplyLeaveDao = managerApplyLeaveDao; 
	} 
	
	
	@Transactional 
	public void saveManagerApplyLeave(ManagerApplyLeave managerApplyLeave) { 
		managerApplyLeaveDao.saveManagerApplyLeave(managerApplyLeave); 
	}  
	
	
	
}

