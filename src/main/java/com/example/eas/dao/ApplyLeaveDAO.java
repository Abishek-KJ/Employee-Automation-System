package com.example.eas.dao; 

import org.springframework.stereotype.Repository;

import com.example.eas.entity.ApplyLeave;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository 
public class ApplyLeaveDAO { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public void saveApplyLeave(ApplyLeave applyLeave) { 
		entityManager.persist(applyLeave); 
	} 

}

