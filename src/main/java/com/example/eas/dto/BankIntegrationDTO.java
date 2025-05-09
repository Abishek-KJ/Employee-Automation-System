package com.example.eas.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class BankIntegrationDTO { 
	
	   @NotBlank(message = "CIB Portal Login ID is required")
	    private String cibLoginId;

	    @NotBlank(message = "Corporation ID is required")
	    private String corporationId;

	    @NotBlank(message = "User ID is required")
	    private String userId;

	    @NotBlank(message = "Bank Name is required")
	    private String bankName;

	    private MultipartFile excelFile;

	    // Getters and Setters
	    public String getCibLoginId() {
	        return cibLoginId;
	    }

	    public void setCibLoginId(String cibLoginId) {
	        this.cibLoginId = cibLoginId;
	    }

	    public String getCorporationId() {
	        return corporationId;
	    }

	    public void setCorporationId(String corporationId) {
	        this.corporationId = corporationId;
	    }

	    public String getUserId() {
	        return userId;
	    }

	    public void setUserId(String userId) {
	        this.userId = userId;
	    }

	    public String getBankName() {
	        return bankName;
	    }

	    public void setBankName(String bankName) {
	        this.bankName = bankName;
	    }

	    public MultipartFile getExcelFile() {
	        return excelFile;
	    }

	    public void setExcelFile(MultipartFile excelFile) {
	        this.excelFile = excelFile;
	    }
}
