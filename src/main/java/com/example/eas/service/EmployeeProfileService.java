package com.example.eas.service;

import org.springframework.stereotype.Service;

import com.example.eas.entity.AddEmployee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Service 
public class EmployeeProfileService { 
	
	@PersistenceContext
	private EntityManager entityManager; 
	
	public AddEmployee getEmployeeByCode(String empCode) { 
		try { 
			return (AddEmployee) entityManager.createQuery("SELECT e FROM AddEmployee e WHERE e.empCode = :empCode")
					.setParameter("empCode", empCode)
					.getSingleResult(); 
		} 
		catch(NoResultException exception) { 
			return null; 
		} 
	} 
} 
