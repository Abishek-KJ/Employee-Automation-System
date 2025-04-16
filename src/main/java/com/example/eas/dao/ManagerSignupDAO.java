package com.example.eas.dao;


import org.springframework.stereotype.Repository;

import com.example.eas.entity.ManagerSignup;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ManagerSignupDAO { 
	
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public void managerCredentials(ManagerSignup managerSignup) { 
		entityManager.persist(managerSignup); 
	} 
	
} 
