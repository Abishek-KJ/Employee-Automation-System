package com.example.eas.entity; 

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "salarycomponent") 
public class SalaryComponents {  
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	
	@Column(name = "salary_date") 
	private LocalDate salaryDate; 
	
	@Column(name = "basic_salary") 
	private short basicSalary; 
	
	@Column(name = "house_rent_allowance") 
	private short houseRentAllowance; 
	
	@Column(name = "conveyance_allowance") 
	private short conveyanceAllowance; 
	
	@Column(name = "medical_allowance") 
	private short medicalAllowance; 
	
	@Column(name = "special_allowance") 
	private short specialAllowance; 
	
	@Column(name = "providentFund") 
	private short providentFund;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id; 
	} 

	public LocalDate getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(LocalDate salaryDate) {
		this.salaryDate = salaryDate;
	}

	public short getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(short basicSalary) {
		this.basicSalary = basicSalary;
	}

	public short getHouseRentAllowance() {
		return houseRentAllowance;
	}

	public void setHouseRentAllowance(short houseRentAllowance) {
		this.houseRentAllowance = houseRentAllowance;
	}

	public short getConveyanceAllowance() {
		return conveyanceAllowance;
	}

	public void setConveyanceAllowance(short conveyanceAllowance) {
		this.conveyanceAllowance = conveyanceAllowance;
	}

	public short getMedicalAllowance() {
		return medicalAllowance;
	}

	public void setMedicalAllowance(short medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}

	public short getSpecialAllowance() {
		return specialAllowance;
	}

	public void setSpecialAllowance(short specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public short getProvidentFund() {
		return providentFund;
	}

	public void setProvidentFund(short providentFund) {
		this.providentFund = providentFund;
	} 
		

}
