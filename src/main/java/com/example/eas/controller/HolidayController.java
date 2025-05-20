package com.example.eas.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.eas.service.HolidayService;

@Controller
public class HolidayController {
	
	private final HolidayService holidayService; 
	
	public HolidayController(HolidayService holidayService) { 
		this.holidayService = holidayService; 
	} 
	
	@GetMapping("/insert-holiday") 
	public String insert() { 
		holidayService.insertHoliday(); 
		return "Holiday inserted successfully!"; 
	} 
	
} 
