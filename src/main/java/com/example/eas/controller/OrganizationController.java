package com.example.eas.controller; 

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.eas.entity.OrganizationDetails;
import com.example.eas.repository.OrganizationRepository;
import com.example.eas.service.OrganizationService;

// import jakarta.persistence.criteria.Path;
import java.nio.file.Path;
import jakarta.servlet.http.HttpServletRequest;

@Controller 
public class OrganizationController { 
	
	private final OrganizationService organizationService; 
	private final OrganizationRepository organizationRepository; 
	
	public OrganizationController(OrganizationService organizationService, OrganizationRepository organizationRepository) { 
		
		this.organizationService = organizationService; 
		this.organizationRepository = organizationRepository; 
	} 
		
/* 	@PostMapping("/admin/save-organization-details") 
	@ResponseBody 
	public String saveOrganizationDetails(@ModelAttribute OrganizationDetails organizationDetails, 
										@RequestParam("logo") MultipartFile logo, 
										HttpServletRequest request, 
										Model model) { 
		if(!logo.isEmpty()) { 
			try { 
				String originalFilename = logo.getOriginalFilename(); 
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); 
				
				String newFileName = organizationDetails.getOrgName().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + extension; 
				
				String uploadDir = request.getServletContext().getRealPath("/images/"); 
				File directory = new File(uploadDir); 
				if(!directory.exists()) { 
					directory.mkdirs(); 
				} 
				
				Path path = (Path) Paths.get(uploadDir, newFileName); 
				Files.write(path, logo.getBytes()); 
				organizationDetails.setLogoPath("/images/" + newFileName); 
			} 
			catch(IOException exception) { 
				exception.printStackTrace(); 
				model.addAttribute("error", "Image upload failed"); 
			} 
		} 
		
		organizationService.save(organizationDetails); 
		return "Aconfiguration"; 		
	} */ 
	
	/* @PostMapping("/admin/save-organization-details")
	public String saveOrganizationDetails(@ModelAttribute OrganizationDetails organization,
	                                      @RequestParam("logo") MultipartFile file,
	                                      HttpServletRequest request,
	                                      RedirectAttributes redirectAttributes) {
	    try {
	        // 1. Validate file
	        if (file.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "Please select an image file to upload.");
	            return "redirect:/admin/organization-form";
	        }

	        // 2. Generate unique filename
	        String originalFilename = file.getOriginalFilename();
	        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
	        String newFileName = UUID.randomUUID().toString() + extension;

	        // 3. Set upload path
	        String uploadDir = request.getServletContext().getRealPath("/images/");
	        File uploadFolder = new File(uploadDir);
	        if (!uploadFolder.exists()) {
	            uploadFolder.mkdirs();
	        }

	        // 4. Save file
	        File destinationFile = new File(uploadDir + File.separator + newFileName);
	        file.transferTo(destinationFile);

	        // 5. Save relative path in DB (e.g., /images/abc123.png)
	        organization.setLogoPath("/images/" + newFileName);

	        // 6. Save to database
	        // organizationRepository.save(organization); 

	        redirectAttributes.addFlashAttribute("message", "Organization details saved successfully!");
	        return "redirect:/admin/organization-form";

	    } catch (IOException e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("message", "Error uploading file.");
	        return "redirect:/admin/organization-form";
	    }
	} */ 
	/* public void saveOrganization(String orgName, double deduction, MultipartFile logoFile) throws IOException {
        // Save the image file
        String fileName = "organization_logo.png";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(logoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Save to DB
        OrganizationDetails org = new OrganizationDetails();
        org.setOrgName(orgName);
        org.setDeduction(deduction);
        org.setLogoPath("/images/" + fileName);  // Assuming /images/ is accessible as static path

        organizationRepository.save(org);
    } */ 
	
	@PostMapping("/admin/save-organization-details")
	public String saveOrganization(String orgName, double deduction, MultipartFile logo) throws IOException {
	    String fileName = "organization_logo.png";
	    String uploadDir = "D:/Employee Automation System/Project/Exports"; 
	    

	    Path uploadPath = Paths.get(uploadDir);
	    if (!Files.exists(uploadPath)) {
	        Files.createDirectories(uploadPath);
	    }

	    Path filePath = uploadPath.resolve(fileName);
	    Files.copy(logo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

	    OrganizationDetails org = new OrganizationDetails();
	    org.setOrgName(orgName);
	    org.setDeduction(deduction);
	    org.setLogoPath("/images/" + fileName); // Accessible via the resource handler

	    organizationRepository.save(org); 
	    System.out.println("Image saved at : " + uploadDir); 
	    return "redirect:/Aconfiguration"; 
	} 
} 




