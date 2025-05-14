package com.example.eas.service; 

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.eas.entity.OrganizationDetails;
import com.example.eas.repository.OrganizationRepository;

@Service 
public class OrganizationService { 
	
	private final OrganizationRepository repository; 
	
	@Value("${upload.directory}") 
	private String uploadDir; 
	
	public OrganizationService(OrganizationRepository repository) { 
		this.repository = repository; 
	} 
	
	public void save(OrganizationDetails organization) { 
		repository.save(organization); 
	}  
	
	public void saveOrganization(String orgName, double deduction, MultipartFile logoFile) throws IOException { 
		String fileName = "organization_logo.png"; 
		Path uploadPath = Paths.get(uploadDir); 
		
		if(!Files.exists(uploadPath)) { 
			Files.createDirectories(uploadPath); 
		} 
		
		Path filePath = uploadPath.resolve(fileName); 
		Files.copy(logoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); 
		
		// Save to database 
		OrganizationDetails organizationDetails = new OrganizationDetails(); 
		organizationDetails.setOrgName(orgName); 
		organizationDetails.setDeduction(deduction); 
		organizationDetails.setLogoPath("/images/" + fileName); 
		
		repository.save(organizationDetails); 
		
	} 
	
	
}

