package com.example.eas.entity; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class OrganizationDetails {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	
	private String orgName; 
	
	private String logoPath; 
	
	private double deduction;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public OrganizationDetails() {
		// super();
	} 

	public OrganizationDetails(Long id, String orgName, String logoPath, double deduction) {
		// super();
		this.id = id;
		this.orgName = orgName;
		this.logoPath = logoPath;
		this.deduction = deduction;
	}
	
}

