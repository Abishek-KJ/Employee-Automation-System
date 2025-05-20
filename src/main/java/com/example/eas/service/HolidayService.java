package com.example.eas.service; 

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.eas.entity.Holiday;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Service 
public class HolidayService { 
	
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	
	@Transactional 
	public void insertHoliday() { 
		Holiday holiday = new Holiday(LocalDate.of(2025, 4, 14), "Tamil New Year"); 
		entityManager.persist(holiday); 
	} 

} 

