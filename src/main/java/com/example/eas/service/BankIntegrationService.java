package com.example.eas.service; 

import org.springframework.stereotype.Service;

import com.example.eas.entity.BankIntegration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/* import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.eas.entity.PayrollExcelUpload;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service 
public class PayrollUploadService {
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public boolean isAlreadyUploaded(int month, int year) { 
		String query = "SELECT COUNT(p) FROM PayrollExcelUploaded p WHERE p.month =: month AND p.year = :year"; 
		Long count = entityManager.createQuery(query, Long.class)
				.setParameter("month", month)
				.setParameter("year", year) 
				.getSingleResult(); 
		return count > 0; 
	} 
	
	public void saveExcelUpload(String cibLogin, String corpId, String userId, byte[] excelBytes, int month, int year, String filename) { 
		PayrollExcelUpload upload = new PayrollExcelUpload(); 
		upload.setCibPortalLogin(cibLogin); 
		upload.setCorporationId(corpId); 
		upload.setUserId(userId); 
		upload.setMonth(month); 
		upload.setYear(year); 
		upload.setUploadDate(LocalDate.now()); 
		upload.setExcelData(excelBytes); 
		upload.setFileName(filename); 
		
		entityManager.persist(upload); 
	} 
} */ 


@Service 
public class BankIntegrationService { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@Transactional 
	public void saveBankIntegration(BankIntegration bankIntegration) { 
		entityManager.persist(bankIntegration); 
	} 
} 

