package com.example.eas.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name="applyleave") 
public class ApplyLeave { 
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int leaveEmployeeId; 
	
	/* @ManyToOne 
	@JoinColumn(name = "emp_code", referencedColumnName = "emp_code", nullable = false) 
	private AddEmployee employeeCode; */ 
	
	@Column(name = "leave_employee_name") 
	private String leaveEmployeeName; 
	
	@Column(name = "leave_type") 
	private String leaveType; 
	
	@Column(name = "leave_from") 
	private LocalDate leaveFrom; 
	
	@Column(name = "leave_to") 
	private LocalDate leaveTo; 
	
	@Column(name = "team_name") 
	private String teamName; 
	
	@Column(name = "description") 
	private String description; 
	
	@Column(name = "status") 
	private int status; 
	
	/* @ManyToOne
	@JoinColumn(name = "id", nullable = false)  
	private AddEmployee addEmployee; */ 

	public ApplyLeave() {
		// super();
	}

	

	public ApplyLeave(int leaveEmployeeId, String leaveEmployeeName, String leaveType, LocalDate leaveFrom,
			LocalDate leaveTo, String teamName, String description, int status) {
		// super();
		this.leaveEmployeeId = leaveEmployeeId;
		this.leaveEmployeeName = leaveEmployeeName;
		this.leaveType = leaveType;
		this.leaveFrom = leaveFrom;
		this.leaveTo = leaveTo;
		this.teamName = teamName;
		this.description = description;
		this.status = status;
	}



	public int getLeaveEmployeeId() {
		return leaveEmployeeId;
	}

	public void setLeaveEmployeeId(int leaveEmployeeId) {
		this.leaveEmployeeId = leaveEmployeeId;
	}

	public String getLeaveEmployeeName() {
		return leaveEmployeeName;
	}

	public void setLeaveEmployeeName(String leaveEmployeeName) {
		this.leaveEmployeeName = leaveEmployeeName;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public LocalDate getLeaveFrom() {
		return leaveFrom;
	}
	 
	public void setLeaveFrom(LocalDate leaveFrom) {
		this.leaveFrom = leaveFrom;
	}

	public LocalDate getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(LocalDate leaveTo) {
		this.leaveTo = leaveTo;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 
	
	public int getStatus() { 
		// System.out.println("Status : " + getStatus()); 
		return status; 
	}

	public void setStatus(int status) {
		this.status = status;
	} 
	
	public String getStatusText() { 
		
		switch(status) { 
			
		case 1 : 
			return "Accepted"; 
		case 2 : 
			return "Rejected"; 
		default : 
			return "Pending"; 
		} 
		
	} 
	 
	
} 
