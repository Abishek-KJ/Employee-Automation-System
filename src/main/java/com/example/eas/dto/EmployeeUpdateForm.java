package com.example.eas.dto;

import java.time.LocalDate;

public class EmployeeUpdateForm { 
	
	private String empCode; 
	private String newEmpCode; 
	private String empName; 
	private String jobRole; 
	private LocalDate joinDate; 
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
	public String getNewEmpCode() {
		return newEmpCode;
	}
	public void setNewEmpCode(String newEmpCode) {
		this.newEmpCode = newEmpCode;
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
	public String getMobile() {
		return mobile;
	}

	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	} 
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setGender(String gender) {
		this.gender = gender;
	} 
	
} 
