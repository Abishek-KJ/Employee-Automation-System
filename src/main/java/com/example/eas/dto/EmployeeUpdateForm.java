package com.example.eas.dto;

import java.time.LocalDate;

public class EmployeeUpdateForm {
	
	private String address; 
	private String city; 
	private String country; 
	private String gender; 
	private LocalDate dob; 
	private String mobile; 
	
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
