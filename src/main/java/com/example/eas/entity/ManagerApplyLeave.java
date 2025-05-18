package com.example.eas.entity; 

import jakarta.persistence.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 


@Entity
@Table(name = "managerapplyleave") 
public class ManagerApplyLeave {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int leaveManagerId; 
	
	@Column(name = "leave_manager_name") 
	private String leaveManagerName; 
	
	
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
	
	public ManagerApplyLeave() {
		// super();
	}

	public ManagerApplyLeave(int leaveManagerId, String leaveManagerName, String leaveType, LocalDate leaveFrom,
			LocalDate leaveTo, String teamName, String description, int status) {
		// super();
		this.leaveManagerId = leaveManagerId;
		this.leaveManagerName = leaveManagerName;
		this.leaveType = leaveType;
		this.leaveFrom = leaveFrom;
		this.leaveTo = leaveTo;
		this.teamName = teamName;
		this.description = description;
		this.status = status;
	}

	public int getLeaveManagerId() {
		return leaveManagerId;
	}

	public void setLeaveManagerId(int leaveManagerId) {
		this.leaveManagerId = leaveManagerId;
	}

	public String getLeaveManagerName() {
		return leaveManagerName;
	}

	public void setLeaveManagerName(String leaveManagerName) {
		this.leaveManagerName = leaveManagerName;
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

