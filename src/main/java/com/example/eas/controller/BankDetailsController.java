package com.example.eas.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.eas.entity.BankDetails;
import com.example.eas.service.BankDetailsService;

@Controller
public class BankDetailsController {
	
	private final BankDetailsService bankDetailsService; 
	
	public BankDetailsController(BankDetailsService bankDetailsService) { 
		this.bankDetailsService = bankDetailsService; 
	} 
	
	@GetMapping("/bank-form") 
	public String showForm(Model model) { 
		model.addAttribute("bankDetails", new BankDetails());  
		return "Adirectdeposit"; 
	} 
	
	/* @PostMapping("/submit-form") 
	public String handleForm(
			@RequestParam("cibLoginId") String cibLoginId, 
			@RequestParam("corporationId") String corporationId, 
			@RequestParam("userId") String userId, 
			@RequestParam("bankName") String bankName, 
			@RequestParam("excelFile") MultipartFile excelFile, 
			Model model) throws IOException { 
		
		BankDetails bankDetails = new BankDetails(); 
		bankDetails.setCibLoginId(cibLoginId); 
		bankDetails.setCorporationId(corporationId); 
		bankDetails.setUserId(userId); 
		bankDetails.setBankName(bankName); 
		bankDetails.setExcelFile(excelFile.getBytes()); 
		
		bankDetailsService.saveBankDetails(bankDetails); 
		
		model.addAttribute("message", "Saved Successfully!"); 
		model.addAttribute("bankDetails", new BankDetails());
		
		return "Aselectbank"; 
	} */ 
	
	@PostMapping("/submit-form")
	public String handleForm(@ModelAttribute BankDetails bankDetails,
	                         @RequestParam("excelFile") MultipartFile excelFile,
	                         Model model) throws IOException {
		System.out.println(bankDetails); 
		bankDetails.setExcelData(excelFile.getBytes()); 

	    bankDetailsService.saveBankDetails(bankDetails);

	    model.addAttribute("message", "Saved Successfully!");
	    model.addAttribute("bankDetails", new BankDetails());

	    return "Aselectbank";
	}

}
