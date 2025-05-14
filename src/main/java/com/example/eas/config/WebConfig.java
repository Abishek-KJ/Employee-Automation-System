package com.example.eas.config; 

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class WebConfig implements WebMvcConfigurer { 
	
	/* @Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) { 
		String uploadPath = System.getProperty("user.dir") + "/images/"; 
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file: " + uploadPath); 
	} */ 
	

    /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String externalPath = "file:" + System.getProperty("user.home") + "/organization-logos/";
        registry.addResourceHandler("/images/**").addResourceLocations(externalPath);
    } */ 
	
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/images/**")
	                .addResourceLocations("file:/D:/Employee Automation System/Project/Exports/");
	    }

} 

