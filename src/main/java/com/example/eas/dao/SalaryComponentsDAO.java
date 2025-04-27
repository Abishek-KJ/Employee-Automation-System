package com.example.eas.dao;

import org.springframework.stereotype.Repository;

import com.example.eas.entity.SalaryComponents;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository 
public class SalaryComponentsDAO { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public void saveEmployee(SalaryComponents salaryComponents) { 
		entityManager.persist(salaryComponents); 
		entityManager.flush(); 
	} 

} 


