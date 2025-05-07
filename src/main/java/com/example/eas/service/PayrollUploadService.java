package com.example.eas.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.eas.entity.PayrollExcelUpload;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service 
public class PayrollUploadService {
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public boolean isAlreadyUploaded(int month, int year) { 
		String query = "SELECT COUNT(p) FROM PayrollExcelUploaded p WHERE p.month AND p.year"; 
		Long count = entityManager.createQuery(query, Long.class)
				.setParameter("month", month)
				.setParameter("year", year) 
				.getSingleResult(); 
		return count > 0; 
	} 
	
	public void saveExcelUpload(String cibLogin, String corpId, String userId, byte[] excelBytes, int month, int year) { 
		PayrollExcelUpload upload = new PayrollExcelUpload(); 
		upload.setCibPortalLogin(cibLogin); 
		upload.setCorporationId(corpId); 
		upload.setUserId(userId); 
		upload.setMonth(month); 
		upload.setYear(year); 
		upload.setUploadDate(LocalDate.now()); 
		upload.setExcelData(excelBytes); 
		
		entityManager.persist(upload); 
	} 
} 
