package com.example.eas.service; 

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eas.controller.EDashboardController;
import com.example.eas.dto.EmployeeSalaryDTO;
import com.example.eas.entity.AddEmployee;
import com.example.eas.entity.ManagerSignup;
import com.example.eas.entity.SalaryComponents;
import com.example.eas.service.EDashboardService.DashboardData;
import com.example.eas.dto.ManagerSalaryDTO; 

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelExportService {
	
	private final SalaryComponentsService salaryComponentsService; 
	
	private final EDashboardController eDashboardController; 
	
	private final EDashboardService dashboardService; 
	
	private final ManageManagerService manageManagerService; 
	
	// @Autowired 
	// private NameSeparation nameSeparation; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	public ExcelExportService(SalaryComponentsService salaryComponentsService, EDashboardController eDashboardController, EDashboardService dashboardService, ManageManagerService manageManagerService) { 
		this.salaryComponentsService = salaryComponentsService; 
		this.eDashboardController = eDashboardController; 
		this.dashboardService = dashboardService; 
		this.manageManagerService = manageManagerService; 
	} 
	
	
    public void  exportEmployeeSalaryToExcel() throws IOException { 
		
		SalaryComponents salaryComponents = salaryComponentsService.getSalaryComponentsForCurrentMonth(); 
		
		List<EmployeeSalaryDTO> employees = salaryComponentsService.getAllEmployees(); 
		
		List<ManagerSalaryDTO> managers = salaryComponentsService.getAllManagers(); 
		
		// Create a workbook and sheet 
		
		Workbook workbook = new XSSFWorkbook(); 
		
		LocalDate currentDate = LocalDate.now(); 
		
		String[] headers = {"Employee code", "Employee name", "Employee designation", "Basic salary", "House Rent Allowance", "Conveyance Allowance", "Medical Allowance", "Special Allowance", "Provident Fund", "totalAmount", "Gross Salary", "LOP Days", "LOP Amount", "Net Payable"}; 
		
		Sheet sheet = workbook.createSheet("Employee salary" + currentDate); 
		
		Row headerRow = sheet.createRow(0); 
		headerRow.createCell(0).setCellValue("Employee code"); 
		headerRow.createCell(1).setCellValue("Employee name"); 
		headerRow.createCell(2).setCellValue("Employee designation"); 
		headerRow.createCell(3).setCellValue("Basic salary"); 
		headerRow.createCell(4).setCellValue("House Rent Allowance"); 
		headerRow.createCell(5).setCellValue("Conveyance Allowance"); 
		headerRow.createCell(6).setCellValue("Medical Allowance"); 
		headerRow.createCell(7).setCellValue("Special Allowance"); 
		headerRow.createCell(8).setCellValue("Provident Fund"); 
		headerRow.createCell(9).setCellValue("Gross Salary"); 
		headerRow.createCell(10).setCellValue("Total Amount"); 
		headerRow.createCell(11).setCellValue("LOP Days");
		headerRow.createCell(12).setCellValue("LOP Amount");
		headerRow.createCell(13).setCellValue("Net Payable");
				
		int rowNum = 1; 
		
		YearMonth yearMonth = YearMonth.now(); 
		int daysInMonth = yearMonth.lengthOfMonth(); 
		
		
		for(EmployeeSalaryDTO employee : employees) { 
			Row row = sheet.createRow(rowNum++); 
			row.createCell(0).setCellValue(employee.getEmpCode()); 
			row.createCell(1).setCellValue(employee.getEmpName()); 
			row.createCell(2).setCellValue(employee.getJobRole()); 
			
			
			BigDecimal yearlyCtc = employee.getCtc(); 
			// BigDecimal months = new BigDecimal("12"); 
			if(yearlyCtc == null || yearlyCtc.compareTo(BigDecimal.ZERO) <= 0) { 
				System.out.println("Skipping employee due to missing/invalid CTC: " + employee.getEmpName()); 
				continue; 
			} 
			
			BigDecimal monthlyCtc  = yearlyCtc.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP); 
			
			// Double monthlyCtcInDouble = monthlyCtc.doubleValue(); 
			
			// System.out.println("Monthly CTC: " + monthlyCtcInDouble); 
			
			BigDecimal basicSalary = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getBasicSalary()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal hra = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getHouseRentAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal conveyanceAllowance = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getConveyanceAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal medicalAllowance = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getMedicalAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal specialAllowance = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getSpecialAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal providentFund = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getProvidentFund()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal grossSalary = basicSalary.add(hra).add(conveyanceAllowance).add(medicalAllowance).add(specialAllowance); 
			BigDecimal totalAmount = basicSalary.add(hra).add(conveyanceAllowance).add(medicalAllowance).add(specialAllowance).add(providentFund); 
			
			
			// LOP Calculation 
			
			DashboardData dashboardData = dashboardService.getDashboardData(employee.getEmpName(), employee.getJobRole()); 
			int lopDays = dashboardData.getTotalDays(); 
			
			BigDecimal perDayGrossSalary = grossSalary.divide(BigDecimal.valueOf(daysInMonth), 2, RoundingMode.HALF_UP); 
			BigDecimal lopAmount = perDayGrossSalary.multiply(BigDecimal.valueOf(lopDays)); 
			BigDecimal netPayable = grossSalary.subtract(lopAmount); 
			System.out.println(employee.getEmpName() + "'s Daily pay: " + netPayable.divide(BigDecimal.valueOf(daysInMonth), 2, RoundingMode.HALF_UP)); 
								
			row.createCell(3).setCellValue(basicSalary.doubleValue()); 
			row.createCell(4).setCellValue(hra.doubleValue()); 
			row.createCell(5).setCellValue(conveyanceAllowance.doubleValue()); 
			row.createCell(6).setCellValue(medicalAllowance.doubleValue()); 
			row.createCell(7).setCellValue(specialAllowance.doubleValue()); 
			row.createCell(8).setCellValue(providentFund.doubleValue()); 
			row.createCell(9).setCellValue(grossSalary.doubleValue()); 			
			row.createCell(10).setCellValue(totalAmount.doubleValue()); 
			row.createCell(11).setCellValue(lopDays); 
			row.createCell(12).setCellValue(lopAmount.doubleValue()); 		
			row.createCell(13).setCellValue(netPayable.doubleValue()); 
			System.out.println("CTC for " + employee.getEmpName() + ": " + employee.getCtc()); 
			System.out.println("Basic % : " + salaryComponents.getBasicSalary()); 
			System.out.println("HRA % : " + salaryComponents.getHouseRentAllowance()); 
			System.out.println("Salary Components for current month : " + salaryComponents.getBasicSalary() + ", " + salaryComponents.getConveyanceAllowance() + ", " + salaryComponents.getHouseRentAllowance() + ", " + salaryComponents.getMedicalAllowance() + ", " + salaryComponents.getProvidentFund() + ", " + salaryComponents.getSpecialAllowance()); 
		} 
		
		for(ManagerSalaryDTO manager : managers) { 
			if(manager.getManagerCTC() == null || manager.getManagerCTC().compareTo(BigDecimal.ZERO) <= 0) { 
				System.out.println("Skipping manager due to invalid CTC : " + manager.getName()); 
				continue; 
			} 
			
			BigDecimal monthlyCtc = manager.getManagerCTC().divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP); 
			BigDecimal basicSalary = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getBasicSalary()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal hra = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getHouseRentAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal conveyance = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getConveyanceAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal medical = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getMedicalAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal special = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getSpecialAllowance()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal pf = monthlyCtc.multiply(BigDecimal.valueOf(salaryComponents.getProvidentFund()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)); 
			BigDecimal gross = basicSalary.add(hra).add(conveyance).add(medical).add(special); 
			BigDecimal total = gross.add(pf); 
			
			DashboardData dashboardData = dashboardService.getDashboardData(manager.getName(), manager.getDesignation()); 
			int lopDays = dashboardData.getTotalDays(); 
			BigDecimal perDayGross = gross.divide(BigDecimal.valueOf(daysInMonth), 2, RoundingMode.HALF_UP); 
			BigDecimal lopAmount = perDayGross.multiply(BigDecimal.valueOf(lopDays));  
			BigDecimal netPay = gross.subtract(lopAmount); 
			
			Row row = sheet.createRow(rowNum++); 
			row.createCell(0).setCellValue(""); 
			row.createCell(1).setCellValue(manager.getName()); 
			row.createCell(2).setCellValue(manager.getDesignation()); 
			row.createCell(3).setCellValue(basicSalary.doubleValue()); 
			row.createCell(4).setCellValue(hra.doubleValue()); 
			row.createCell(5).setCellValue(conveyance.doubleValue()); 
			row.createCell(6).setCellValue(medical.doubleValue()); 
			row.createCell(7).setCellValue(special.doubleValue()); 
			row.createCell(8).setCellValue(pf.doubleValue()); 
			row.createCell(9).setCellValue(gross.doubleValue()); 
			row.createCell(10).setCellValue(total.doubleValue()); 
			row.createCell(11).setCellValue(lopDays); 
			row.createCell(12).setCellValue(lopAmount.doubleValue()); 
			row.createCell(13).setCellValue(netPay.doubleValue()); 
			
		} 
					
		for(int i = 0; i < headers.length; i++) { 
			sheet.autoSizeColumn(i); 
		} 
		
		
		// response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		// response.setHeader("Content-Disposition", "attachment; filename=employee_salary.xlsx"); 

		// workbook.write(response.getOutputStream()); 
		String folderPath = "D:/Employee Automation System/Project/Exports/"; 
		String fileName = "employee_salary_" + currentDate + ".xlsx"; 
		File file = new File(folderPath, fileName); 
		java.nio.file.Path fullPath = Paths.get(folderPath, fileName); 
		java.nio.file.Files.createDirectories(java.nio.file.Paths.get(folderPath)); 
		
		try(java.io.FileOutputStream fileOut = new java.io.FileOutputStream(file)){ 
			workbook.write(fileOut); 
			fileOut.flush(); 
		} 
		
		workbook.close(); 	
		
		System.out.println("Excel file saved at: " + fileName); 
		

	} 

	
} 

