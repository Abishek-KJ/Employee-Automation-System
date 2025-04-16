package com.example.eas.dao;

import org.springframework.stereotype.Repository;

import com.example.eas.entity.AddEmployee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/* public interface AddEmployeeDAO{ 
	
	void save(AddEmployee addEmployee); 
	
} */ 

@Repository 
public class AddEmployeeDAO{ 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public void saveEmployee(AddEmployee addEmployee) { 
		entityManager.persist(addEmployee); 
	} 
} 






