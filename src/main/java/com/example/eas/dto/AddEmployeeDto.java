package com.example.eas.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class AddEmployeeDto { 
	
	private String empCode; 
	
	
	private String empName; 
	

	private String jobRole; 

	
	private LocalDate joinDate; 
	

	private String mailId; 
	
	
	private String password; 
	
	
	private String address; 
	
	
	private String city; 
	
	
	private String country; 
	
	
	private String gender; 
	
	
	private LocalDate dob; 

	
	private String mobile;


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


	public LocalDate getJoinDate() {
		return joinDate;
	}


	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}


	public String getMailId() {
		return mailId;
	}


	public void setMailId(String mailId) {
		this.mailId = mailId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public LocalDate getDob() {
		return dob;
	}


	public void setDob(LocalDate dob) {
		this.dob = dob;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	} 
	

	public AddEmployeeDto() {
		// super();
	} 
	


	public AddEmployeeDto(String empCode, String empName, String jobRole, LocalDate joinDate, String mailId,
			String password, String address, String city, String country, String gender, LocalDate dob, String mobile) {
		// super();
		this.empCode = empCode;
		this.empName = empName;
		this.jobRole = jobRole;
		this.joinDate = joinDate;
		this.mailId = mailId;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.gender = gender;
		this.dob = dob;
		this.mobile = mobile;
	}

	 

}
