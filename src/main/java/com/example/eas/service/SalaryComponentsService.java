package com.example.eas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eas.entity.AddEmployee;
import com.example.eas.entity.SalaryComponents;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service 
// @Transactional 
public class SalaryComponentsService { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@Transactional 
	public SalaryComponents saveSalaryComponents(SalaryComponents salaryComponents) { 
		entityManager.persist(salaryComponents); 
		return salaryComponents; 
	} 
	
	public SalaryComponents getSalaryComponentsForCurrentMonth() { 
		LocalDate currentDate = LocalDate.now(); 
		String queryString = "SELECT s FROM SalaryComponent s WHERE s.salaryDate = :salaryDate"; 
		TypedQuery<SalaryComponents> query = entityManager.createQuery(queryString, SalaryComponents.class);  
		query.setParameter("salaryDate", currentDate); 
		return query.getSingleResult(); 
	}
	
	 public List<AddEmployee> getAllSalaryComponents(){ 
		 
		 String queryString = "SELECT e.empCode, e.empName, e.ctc FROM AddEmployee e"; 
		 TypedQuery<AddEmployee> query = entityManager.createQuery(queryString, AddEmployee.class); 
		 return query.getResultList(); 
	 }
} 
