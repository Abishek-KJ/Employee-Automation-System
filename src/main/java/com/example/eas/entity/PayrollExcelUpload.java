package com.example.eas.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity 
@Table(name = "payroll_excel_uploads", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"month", "year"})
})  
public class PayrollExcelUpload {
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	
	private Long id; 
	
	@Column(name = "cib_portal_login") 
	private String cibPortalLogin; 
	
	@Column(name = "corporation_id") 
	private String corporationId; 
	
	@Column(name = "user_id") 
	private String userId; 
	
	@Column(name = "file_name") 
	private String fileName; 
	
	@Column(name = "month") 
	private int month; 
	
	@Column(name = "year") 
	private int year; 
	
	@Column(name = "upload_date") 
	private LocalDate uploadDate; 
	
	@Lob 
	@Column(name = "excel_data", columnDefinition = "LONGLOB") 
	private byte[] excelData; 
		
	public PayrollExcelUpload() {
		// super();
	}
	
	public PayrollExcelUpload(Long id, String cibPortalLogin, String corporationId, String userId, String fileName,
			int month, int year, LocalDate uploadDate, byte[] excelData) { 
		// super();
		this.id = id;
		this.cibPortalLogin = cibPortalLogin;
		this.corporationId = corporationId;
		this.userId = userId;
		this.fileName = fileName;
		this.month = month;
		this.year = year;
		this.uploadDate = uploadDate; 
		this.excelData = excelData; 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCibPortalLogin() {
		return cibPortalLogin;
	}

	public void setCibPortalLogin(String cibPortalLogin) {
		this.cibPortalLogin = cibPortalLogin;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public byte[] getExcelData() {
		return excelData;
	}

	public void setExcelData(byte[] excelData) {
		this.excelData = excelData;
	} 
	
}
