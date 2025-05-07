package com.example.eas.dto;

import java.math.BigDecimal;

public class EmployeeSalaryDTO {
	
	private String empCode; 
	private String empName; 
	private String jobRole; 
	private BigDecimal ctc; 
	
	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public BigDecimal getCtc() {
		return ctc;
	}

	public void setCtc(BigDecimal ctc) {
		this.ctc = ctc;
	}

	public EmployeeSalaryDTO(String empCode, String empName, String jobRole, BigDecimal ctc) { 
		this.empCode = empCode; 
		this.empName = empName; 
		this.jobRole = jobRole; 
		this.ctc = ctc; 
	} 
	
} 

