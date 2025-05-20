package com.example.eas.dao; 

import org.springframework.stereotype.Repository;

import com.example.eas.entity.ManagerApplyLeave;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext; 

@Repository 
public class ManagerApplyLeaveDAO { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public void saveManagerApplyLeave(ManagerApplyLeave managerApplyLeave) { 
		entityManager.persist(managerApplyLeave); 
	} 

}

