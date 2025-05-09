package com.example.eas.service;

import org.springframework.stereotype.Service;

import com.example.eas.entity.BankDetails;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service 
public class BankDetailsService {
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@Transactional 
	public void saveBankDetails(BankDetails bankDetails) { 
		entityManager.persist(bankDetails); 
	} 
} 


