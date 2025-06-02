package com.example.eas.service; 


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eas.dao.ManagerSignupDAO;
import com.example.eas.entity.ManagerSignup; 
import com.example.eas.utility.SessionChecker; 

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service 
public class ManagerSignupService {
	
	
	
	@Autowired 
	private ManagerSignupDAO managerSignupDAO; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	private final SessionChecker sessionChecker; 
	
	public ManagerSignupService(SessionChecker sessionChecker) { 
		this.sessionChecker = sessionChecker; 
	} 
	
	@Transactional 
	public void saveManagerSignup(ManagerSignup managerSignup) { 
		managerSignupDAO.managerCredentials(managerSignup); 
	} 
	
	public boolean verifyManager(String managerMailId, String password) { 
		
		try { 
			String query = "SELECT m FROM ManagerSignup m WHERE m.managerMailId = :managerMailId AND m.password = :password"; 
			ManagerSignup managerSignup = entityManager.createQuery(query, ManagerSignup.class).setParameter("managerMailId", managerMailId).setParameter("password", password).getSingleResult(); 
			return managerSignup != null; 
		} 
		catch(NoResultException exception) { 
			return false; 
		} 
	} 
	
	
} 
