package com.example.eas.dto; 

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.eas.service.ManageManagerService;
import com.example.eas.utility.NameSeparation;

public class ManagerSalaryDTO { 
	
	private String name; 
			
	private String managerMailId; 
	
	private BigDecimal managerCTC; 
	
	private String designation; 
	
	// @Autowired 
	// private NameSeparation nameSeparation; 
	
	/* public ManagerSalaryDTO(NameSeparation nameSeparation) { 
		this.nameSeparation = nameSeparation; 
	} */ 
	
	@Autowired 
	private ManageManagerService manageManagerService; 
	
	public ManagerSalaryDTO() {
		// super();
	} 

	public ManagerSalaryDTO(String name, String managerMailId, BigDecimal managerCTC) {
		// super();
		this.name = name;
		this.managerMailId = managerMailId;
		this.managerCTC = managerCTC;
		// this.designation = this.manageManagerService.extractDesignationFromEmail(managerMailId); 
		// this.designation = manageManagerService.extractDesignationFromEmail(managerMailId); 
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerMailId() {
		return managerMailId;
	}

	public void setManagerMailId(String managerMailId) {
		this.managerMailId = managerMailId;
		// this.designation = nameSeparation.extractDesignationFromEmail(managerMailId); 
		// this.designation = manageManagerService.extractDesignationFromEmail(managerMailId); 
	}

	public BigDecimal getManagerCTC() {
		return managerCTC;
	}

	public void setManagerCTC(BigDecimal managerCTC) {
		this.managerCTC = managerCTC;
	}

	public String getDesignation() {
		// return designation; 
		return NameSeparation.extractDesignationFromEmail(managerMailId); 
	} 

	public void setDesignation(String designation) {
		this.designation = designation;
	} 
	
	
	
	

}
