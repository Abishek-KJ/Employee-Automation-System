package com.example.eas.entity; 


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id; 

@Entity 
@Table(name="department")
public class AddDepartment { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; 
	
	@Column(name = "dep_code") 
	private String depCode; 
	
	@Column(name = "dep_name") 
	private String depName; 
	
	@Column(name = "dep_description") 
	private String depDescription; 

	public AddDepartment() {
		// super();
	} 

	public AddDepartment(int id, String depCode, String depName, String depDescription) {
		// super();
		this.id = id;
		this.depCode = depCode;
		this.depName = depName;
		this.depDescription = depDescription;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepDescription() {
		return depDescription;
	}

	public void setDepDescription(String depDescription) {
		this.depDescription = depDescription;
	} 
	
	

} 
