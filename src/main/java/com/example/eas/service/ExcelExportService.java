package com.example.eas.service;

import org.springframework.stereotype.Service;

@Service
public class ExcelExportService {
	
	private final SalaryComponentsService salaryComponentsService; 
	
	public ExcelExportService(SalaryComponentsService salaryComponentsService) { 
		this.salaryComponentsService = salaryComponentsService; 
	} 
	
	
}
