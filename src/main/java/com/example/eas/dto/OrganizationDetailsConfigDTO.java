package com.example.eas.dto; 

import org.springframework.web.multipart.MultipartFile;

public class OrganizationDetailsConfigDTO { 
	
	
	private String orgName; 
	
	private Double deduction; 
	
	private	MultipartFile logo;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Double getDeduction() {
		return deduction;
	}

	public void setDeduction(Double deduction) {
		this.deduction = deduction;
	}

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	public OrganizationDetailsConfigDTO(String orgName, Double deduction, MultipartFile logo) {
		// super();
		this.orgName = orgName;
		this.deduction = deduction;
		this.logo = logo;
	}

	public OrganizationDetailsConfigDTO() {
		// super();
	} 
}

