package com.example.eas.controller; 

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eas.service.ExcelExportService;

import jakarta.servlet.http.HttpServletResponse;

@Controller 
@RequestMapping("/export") 
public class ExcelExportController { 
	
	private final ExcelExportService excelExportService; 
	
	public ExcelExportController(ExcelExportService excelExportService) { 
		this.excelExportService = excelExportService; 
	} 
	
	@GetMapping("/salary")
	public String exportSalaryToExcel(HttpServletResponse response) throws IOException { 
		System.out.println("Inside the exportSalaryToExcel(HttpServletResponse response) method."); 
		excelExportService.exportEmployeeSalaryToExcel(); 
		return "Aselectbank"; 
	} 

} 

