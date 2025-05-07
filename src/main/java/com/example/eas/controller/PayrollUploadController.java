package com.example.eas.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.eas.entity.PayrollExcelUpload;
import com.example.eas.service.PayrollUploadService;

import jakarta.persistence.EntityManager;

// Controller for excel sheet upload to database 

@Controller 
public class PayrollUploadController { 
	
	private final PayrollUploadService payrollUploadService; 
	
	private final EntityManager entityManager; 
	
	public PayrollUploadController(PayrollUploadService payrollUploadService, EntityManager entityManager) { 
		this.payrollUploadService = payrollUploadService; 
		this.entityManager = entityManager; 
	} 
		
	@PostMapping("/upload-to-db") 
	public ResponseEntity<String> uploadToDatabase(@RequestParam String cibLogin, 
			@RequestParam String corpId, 
			@RequestParam String userId, 
			@RequestParam("file") MultipartFile file) throws IOException { 
		
		String filename = file.getOriginalFilename(); 
		
		if(filename == null || !filename.matches("employee_salary_\\d{4}-\\d{2}-\\d{2}\\.xlsx")) { 
			return ResponseEntity.badRequest().body("Invalid file name format. Use : employee_salary_YYYY-MM-DD.xlsx"); 
		} 
		
		// Extract data from filename 
		String dataStr = filename.substring("employee_salary_".length(), "employee_salary_2025-05-05".length() - 5); 
		LocalDate date = LocalDate.parse(dataStr); 
		
		int month = date.getMonthValue(); 
		int year = date.getYear(); 
		
		if(payrollUploadService.isAlreadyUploaded(month, year)) { 
			return ResponseEntity.badRequest().body("Excel already uploaded for this month."); 
		} 
		payrollUploadService.saveExcelUpload(cibLogin, corpId, userId, file.getBytes(), month, year); 
		return ResponseEntity.ok("Excel file uploaded and stored in DB for " + month + "/" + year);  
	} 
	
	@GetMapping("/download-from-db") 
	public ResponseEntity<ByteArrayResource> downloadExcel(@RequestParam int month, 
			@RequestParam int year, 
			@RequestParam String userId){ 
		String query = "SELECT p FROM PayrollExcelUpload p WHERE p.month = :month AND p.year = :year AND p.userId = :userId"; 
		List<PayrollExcelUpload> results = entityManager.createQuery(query, PayrollExcelUpload.class)
				.setParameter("month", month) 
				.setParameter("year", year) 
				.setParameter("userId", userId) 
				.getResultList(); 
		
		
		if(results.isEmpty()) { 
			return ResponseEntity.notFound().build(); 
		} 
		
		PayrollExcelUpload upload = results.get(0); 
		ByteArrayResource resource = new ByteArrayResource(upload.getExcelData()); 
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payroll_" + month + "_" + year + ".xlsx")
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource); 		
	} 

} 


