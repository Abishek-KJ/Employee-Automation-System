package com.example.eas.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eas.dao.ManagerSignupDAO;
import com.example.eas.entity.ManagerSignup;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service 
public class ManagerSignupService {
	
	@Autowired 
	private ManagerSignupDAO managerSignupDAO; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
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
