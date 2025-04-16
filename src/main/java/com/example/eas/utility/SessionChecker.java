package com.example.eas.utility;

import java.util.Enumeration;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionChecker { 
	
	// Check session values by using this method. 
	
		public boolean isSessionKeyPresent(HttpSession session, String searchKey) { 
			
			Enumeration<String> sessionKeys = session.getAttributeNames(); 
			
			while(sessionKeys.hasMoreElements()) { 
				String key = sessionKeys.nextElement(); 
				if(key.equals(searchKey)) { 
					System.out.println("Found session key : " + key + " with value: " + session.getAttribute(key)); 
					return true; 
				} 
			} 
			System.out.println("Session key : '" + searchKey + "' not found."); 
			return false; 		
		} 

}
