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
@Table(name = "bank_integration") 
public class BankIntegration {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	
	@Column(name = "cib_login_id") 
	private String cibLoginId; 
	
	@Column(name = "corporation_id") 
	private String corporationId; 
	
	@Column(name = "user_id") 
	private String userId; 
	
	@Column(name = "bank_name") 
	private String bankName; 
	
	@Column(name = "file_name") 
	private String fileName; 
		
	@Column(name = "upload_date") 
	private LocalDate uploadDate; 
	
	@Lob 
	@Column(name = "excel_file", columnDefinition = "LONGBLOB") 
	private byte[] excelFile; 
	
		
	public BankIntegration() { 
		// super();
	}
	
	public BankIntegration(Long id, String cibLoginId, String corporationId, String userId, String fileName, LocalDate uploadDate, byte[] excelFile) { 
		// super();
		this.id = id;
		this.cibLoginId = cibLoginId; 
		this.corporationId = corporationId;
		this.userId = userId;
		this.fileName = fileName;
		this.uploadDate = uploadDate; 
		this.excelFile = excelFile; 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public byte[] getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(byte[] excelFile) {
		this.excelFile = excelFile;
	}	
}
