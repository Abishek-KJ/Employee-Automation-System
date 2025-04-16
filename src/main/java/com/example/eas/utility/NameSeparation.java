package com.example.eas.utility;

import org.springframework.stereotype.Component;

@Component 
public class NameSeparation { 
	
	public String extractNameAndDesignation(String email) { 
		if(email == null || !email.contains("@") || !email.contains(".")) { 
			throw new IllegalArgumentException("Invalid email format"); 
		} 
		String beforeAt = email.split("@")[0]; 
		String[] parts = beforeAt.split("\\."); 
		
		if(parts.length < 2) { 
			throw new IllegalArgumentException("Email must contain name."); 
		} 
		
		String designation = parts[parts.length - 1]; 
		
		StringBuilder nameBuilder = new StringBuilder(); 
		for(int i = 0; i < parts.length - 1; i = i + 1) { 
			
			if(i > 0) { 
				nameBuilder.append(" "); 
			} 
			// nameBuilder.append(capitalize(parts[i])); 
		} 
		
		System.out.println(parts[0]); 
		
		return parts[0]; 
	} 

}  
