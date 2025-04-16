package com.example.eas.entity;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee") 
public class AddEmployee { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private int id; 
	
	@Column(name = "emp_code") 
	private String empCode; 
	
	@Column(name = "emp_name")
	private String empName; 
	
	@Column(name = "job_role")
	private String jobRole; 
	
	@Column(name = "join_date") 
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private LocalDate joinDate; 
	
	@Column(name = "mail_id") 
	private String mailId; 
	
	@Column(name = "password") 
	private String password; 
	
	@Column(name = "address") 
	private String address; 
	
	@Column(name = "city") 
	private String city; 
	
	@Column(name = "country") 
	private String country; 
	
	@Column(name = "gender") 
	private String gender; 
	
	@Column(name = "dob") 
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private LocalDate dob; 

	@Column(name = "mobile") 
	private String mobile;
	 
	
	public int getId() {
		return id;
	}

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
	
	public void setId(int id) {
		this.id = id;
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
	
	public AddEmployee() { 
		
	}

	public AddEmployee(int id, String empCode, String empName, String jobRole, LocalDate joinDate, String mailId,
			String password, String address, String city, String country, String gender, LocalDate dob, String mobile) {
		// super();
		this.id = id;
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

	@Override
	public String toString() {
		return "AddEmployee [id=" + id + ", empCode=" + empCode + ", empName=" + empName + ", jobRole=" + jobRole
				+ ", joinDate=" + joinDate + ", mailId=" + mailId + ", password=" + password + ", address=" + address
				+ ", city=" + city + ", country=" + country + ", gender=" + gender + ", dob=" + dob + ", mobile="
				+ mobile + "]";
	} 
	
}  
