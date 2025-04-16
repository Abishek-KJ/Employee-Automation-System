package com.example.eas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eas.dao.AddDepartmentDAO;
import com.example.eas.dao.AddEmployeeDAO;
import com.example.eas.entity.AddDepartment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


@Service 
public class AddDepartmentService {
	
	
	@Autowired 
	private AddDepartmentDAO addDepartmentDAO; 
	
	@PersistenceContext
	private EntityManager entityManager; 
	
	@Transactional 
	public void saveDepartment(AddDepartment addDepartment) { 
		addDepartmentDAO.saveDepartment(addDepartment); 
	} 
	
	public List<AddDepartment> getAllDepartments(){ 
		TypedQuery<AddDepartment> departmentQuery = entityManager.createQuery("SELECT e FROM AddDepartment e", AddDepartment.class);
		return departmentQuery.getResultList(); 
	} 
	
	
}
