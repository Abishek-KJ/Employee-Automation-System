package com.example.eas.controller; 

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
// import java.net.http.HttpHeaders;
import java.time.LocalDate;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eas.exception.NotEligibleException;
import com.example.eas.exception.SalaryNotCreditedException;
import com.example.eas.service.PayrollService;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Controller 
public class PayrollController { 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	private final PayrollService payrollService; 
	
	public PayrollController(PayrollService payrollService) { 
		this.payrollService = payrollService; 
	} 
			
	@GetMapping("/generate-pdf") 
	public ResponseEntity<?> generatePayrollPdf(@RequestParam("employeeCode") String employeeCode, @RequestParam("month") int month, @RequestParam("year") int year, Model model) throws IOException{  
		
		try { 
			ByteArrayResource pdf = payrollService.generatePayrollPdf(employeeCode, month, year); 
		
		
		return ResponseEntity.ok()	
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + employeeCode + "_payroll.pdf")
				.contentType(MediaType.APPLICATION_PDF) 
				.contentLength(pdf.contentLength())
				.body(pdf); 
		} 
		catch(SalaryNotCreditedException | NotEligibleException exception) { 
			// model.addAttribute("error", exception.getMessage()); 
			return ResponseEntity.status(302)
					.header(HttpHeaders.LOCATION, "/payroll-form?error=" + URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8))
					.build(); 
		} 
		catch(IOException exception) { 
			// model.addAttribute("error", "An unexpected error occured."); 
			// return "Epayroll"; 
			return ResponseEntity.status(302)
					.header(HttpHeaders.LOCATION, "/payroll-form?error=" + URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8))
					.build(); 
		} 
	} 
	
	@GetMapping("/payroll-form") 
	public String payrollForm(@RequestParam(value = "error", required = false) String error, Model model) { 
		model.addAttribute("error", error); 
		return "Epayroll"; 
	} 
	
} 


