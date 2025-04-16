package com.example.eas.dao;

import org.springframework.stereotype.Repository;

import com.example.eas.entity.AddDepartment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository 
public class AddDepartmentDAO {
	
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public void saveDepartment(AddDepartment addDepartment) { 
		entityManager.persist(addDepartment); 
	} 
	
	
	
}
