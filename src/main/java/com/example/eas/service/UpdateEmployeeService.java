package com.example.eas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eas.dto.EmployeeUpdateForm;
import com.example.eas.repository.UpdateEmployeeRepository;

@Service 
public class UpdateEmployeeService { 
	
	// @Autowired 
	private final UpdateEmployeeRepository updateEmployeeRepository; 
	
	public UpdateEmployeeService(UpdateEmployeeRepository updateEmployeeRepository) { 
		this.updateEmployeeRepository = updateEmployeeRepository; 
	} 
	
	public void updateInformation(String employeeCode, EmployeeUpdateForm form) { 
		updateEmployeeRepository.updateEmployeeInformation(employeeCode, form.getAddress(), form.getCity(), form.getCountry(), form.getGender(), form.getDob(), form.getMobile()); 
	} 
	
} 
