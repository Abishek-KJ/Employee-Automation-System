package com.example.eas.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "manager") 
public class ManagerSignup { 
		
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; 
	
	@Column(name = "name") 
	private String name; 
	
	@Column(name = "managerMailId") 
	private String managerMailId; 
	
	@Column(name = "password") 
	private String password;
	
	
	public ManagerSignup(int id, String name, String managerMailId, String password) {
		// super();
		this.id = id;
		this.name = name;
		this.managerMailId = managerMailId;
		this.password = password;
	}

	public ManagerSignup() {
		// super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerMailId() {
		return managerMailId;
	}

	public void setManagerMailId(String managerMailId) {
		this.managerMailId = managerMailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
	
	
	
	
	
	

}
