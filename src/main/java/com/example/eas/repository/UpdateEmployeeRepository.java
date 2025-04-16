package com.example.eas.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository 
public class UpdateEmployeeRepository { 
	
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@Transactional 
	public void updateEmployeeInformation(String employeeCode, String address, String city, String country, String gender, LocalDate dob, String mobile) {  
		
		StringBuilder query = new StringBuilder("UPDATE AddEmployee e SET "); 
		Map<String, Object> parameters = new HashMap<>(); 
		
		if(address != null && !address.isEmpty()) { 
			query.append("e.address = :address, "); 
			parameters.put("address", address); 
		} 
		if(city != null && !city.isEmpty()) { 
			System.out.println("New city : " + city); 
			query.append("e.city = :city, "); 
			parameters.put("city", city); 
		} 
		if(country != null && !country.isEmpty()) { 
			query.append("e.country = :country, "); 
			parameters.put("country", country); 
		} 
		if(gender != null && !gender.isEmpty()) { 
			query.append("e.gender = :gender, "); 
			parameters.put("gender", gender); 
		} 
		if(dob != null) { 
			System.out.println("New birthdate : " + dob); 
			query.append("e.dob = :dob, "); 
			parameters.put("dob", dob); 
		} 
		if(mobile != null && !mobile.isEmpty()) { 
			query.append("e.mobile = :mobile, "); 
			parameters.put("mobile", mobile); 
		} 
		
		System.out.println("Date Of Birthdate : " + dob); 
		System.out.println("Mobile Number : " + mobile); 
		
		// Remove the last comma and space 
		if(parameters.size() > 0) { 
			query.setLength(query.length() - 2); 
			query.append(" WHERE e.empCode = :employeeCode"); 
			parameters.put("employeeCode", employeeCode); 
			System.out.println("Final query : " + query.toString()); 
			
			Query jpaQuery = entityManager.createQuery(query.toString()); 
			for(Map.Entry<String, Object> entry : parameters.entrySet()) { 
				jpaQuery.setParameter(entry.getKey(), entry.getValue()); 
			} 
			
			jpaQuery.executeUpdate(); 
		} 		
		
	} 
	
} 
