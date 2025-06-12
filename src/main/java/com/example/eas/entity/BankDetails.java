package com.example.eas.entity; 

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity 
// @Getter 
// @Setter 
@Table(name = "bank_details") 
public class BankDetails { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
		
	@Column(name = "cib_login_id") 
	private String cibLoginId; 
	
	@Column(name = "corporation_id") 
	private String corporationId; 

	@Column(name = "user_id") 
	private String userId; 
	
	@Transient // Prevent persistence
	private MultipartFile excelFile; // For receiving the uploaded file (used in form)
	
	@Lob 
	@Column(name = "excel_file", columnDefinition = "LONGBLOB")  
	private byte[] excelData;
	
	@Column(name = "bank_name") 
	private String bankName; 
	

	
	public BankDetails() {
		// super();
	}
	
	public BankDetails(Long id, String cibLoginId, String corporationId, String userId, MultipartFile excelFile,
			byte[] excelData, String bankName) {
		super();
		this.id = id;
		this.cibLoginId = cibLoginId;
		this.corporationId = corporationId;
		this.userId = userId;
		this.excelFile = excelFile;
		this.excelData = excelData;
		this.bankName = bankName;
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

	public MultipartFile getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(MultipartFile excelFile) {
		this.excelFile = excelFile;
	}

	public byte[] getExcelData() {
		return excelData;
	}

	public void setExcelData(byte[] excelData) {
		this.excelData = excelData;
	}

		
}
