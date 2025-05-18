package com.example.eas.controller; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.eas.entity.ApplyLeave;
import com.example.eas.entity.ManagerSignup;
import com.example.eas.service.ApplyLeaveService;
import com.example.eas.service.EmailService;
import com.example.eas.utility.NameSeparation;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

@Controller 
@RequestMapping("/apply-leave") 
public class ApplyLeaveController { 
		
	@Autowired 
	private ApplyLeaveService applyLeaveService; 
	
	@PersistenceContext 
	private EntityManager entityManager; 
	
	@Autowired 
	private ManagerSignupController managerSignupController; 
	
	@Autowired 
	private EmailService emailService; 
	
	@Autowired 
	private HttpSession httpSession; 
	
	@Autowired 
	private NameSeparation nameSeparation; 
	
	
	// Add leaves to database 
	
	@GetMapping("/show") 	
	public String showApplyLeave(Model model) { 
		String employeeName = (String) httpSession.getAttribute("employeeName"); 
		System.out.println(employeeName); 
		
		ApplyLeave applyLeave = new ApplyLeave(); 
		applyLeave.setLeaveEmployeeName(employeeName); 
		model.addAttribute("applyLeave", applyLeave); 
		model.addAttribute("employeeName", employeeName); 
		model.addAttribute("employeeJobRole", applyLeaveService.getEmployeeJobRole()); 
		model.addAttribute("applyLeave", new ApplyLeave()); 
		return "Eapplyleave"; 
	} 
	
	// Redirects to apply leave page 
	
	@PostMapping("/apply") 
	public String applyLeave(@ModelAttribute ApplyLeave applyLeave, HttpSession session) throws MessagingException { 
		// ApplyLeave applyLeave = (ApplyLeave) model.addAttribute("applyLeave", new ApplyLeave()); 
		
	
		// Mail Service 
		String employeeName = (String) session.getAttribute("employeeName"); 
		applyLeave.setLeaveEmployeeName(employeeName); 
		
		String jobRole = (String) session.getAttribute("employeeJobRole"); 
		applyLeave.setTeamName(jobRole); 
		
		// Leave request will be sent to manager 
		applyLeaveService.saveApplyLeave(applyLeave); 

		String employeeEmail = (String) session.getAttribute("loggedInEmail"); 
		
		
		if(jobRole == null || employeeEmail == null) { 
			return "redirect:/"; 
		} 
		
		String managerEmail = applyLeaveService.findManagerEmail(jobRole); 
		
		if(managerEmail != null) { 
			String subject = "New Leave Request From : " + employeeName;  
			String takeActionLink = "http://localhost:8080/manager/show#"; 
			String body = "<p>An employee (" + employeeName + ") with job role " + jobRole + " has applied for leave.</p>" 
							+ "<p>To take action and view more details : <a href = '"+ takeActionLink +"'>Click here</a></p>" ; 
			emailService.sendEmail("kjabishek2003@gmail.com", employeeEmail, subject, body); 
		} 		
		
		return "redirect:/apply-leave/show"; 
	} 
	
	// Shows leave requests to respective managers 
	
	@GetMapping("/all-roles") 
	public String getAllRolesLeaves(Model model) { 
		List<ApplyLeave> allRoles = applyLeaveService.getLeaveRequestByTeam(managerSignupController.getExtractJobRole()); 
		model.addAttribute("allRoles", allRoles); 
		return "Mactionrequired"; 
	} 
	
	// Shows Action taken to respective managers 
	
	@GetMapping("/action-taken-by-manager") 
	public String getAllActionTakenLeaves(Model model) { 
		List<ApplyLeave> allActionTakenLeaves = applyLeaveService.getActionTakenLeaveByManager(managerSignupController.getExtractJobRole()); 
		model.addAttribute("allActionTakenLeaves", allActionTakenLeaves); 
		return "Mtookaction"; 	
	} 
	
	
	@Transactional 
	@PutMapping("/updateStatus/{id}/{status}") 
	public ResponseEntity<String> updateLeaveStatus(@PathVariable Long id, @PathVariable int status) throws MessagingException{ 
		ApplyLeave applyLeave = entityManager.find(ApplyLeave.class,id); 
		if(applyLeave == null) { 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave request not found"); 	
		} 
		applyLeaveService.updateStatus(id, status); 
		applyLeave.setStatus(status); 
		entityManager.merge(applyLeave); 
		String managerMailId = (String) httpSession.getAttribute("managerLoggedInEmail"); 
		String managerName = nameSeparation.extractNameAndDesignation(managerMailId); 
		System.out.println("Status : " + status); 
		String statusMessage = (status == 1) ? "Approved" : "Rejected"; 
		String subject = "Leave Request : " + statusMessage; 
		String moreAction = "http://localhost:8080/manager/show#"; 
		if(statusMessage.equals("Approved")) { 
			String body = "<p>Dear employee "  +  ",</p>" 
					  	  + "<p>Your leave request has been <b> " + statusMessage + "</b>.</p>" 
					      +"<p>Best regards,</p>" 
					      +"<p>" + managerName + "</p>"; 
			emailService.sendEmail("kjabishek2003@gmail.com", "employeeautomationsystem@gmail.com", subject, body); 
		}
		else { 
			String body = "<p>Dear employee "  +  ",</p>" 
					  + "<p>Your leave request has been <b> " + statusMessage + "</b>.</p>" 
					  +"<p> For more action : <a href = '" + moreAction + "'>Click here</a></p>"
					  +"<p>Best regards,</p>" 
					  +"<p>" + managerName + "</p>"; 
		emailService.sendEmail("kjabishek2003@gmail.com", "employeeautomationsystem@gmail.com", subject, body); 
		} 
	
		return ResponseEntity.ok("Status updated successfully"); 
	} 
	
	@GetMapping("/leave-history") 
	public String getLeaveHistoryForEmployee(Model model, HttpSession session){ 
		
		String leaveEmployeeName = (String) session.getAttribute("employeeName"); 
		String teamName = (String) session.getAttribute("employeeJobRole"); 
		List<ApplyLeave> leaveHistory = applyLeaveService.getLeaveHistory(leaveEmployeeName, teamName); 
		model.addAttribute("leaveHistory", leaveHistory); 
		return "Eleavehistory"; 
	} 
	
	
	
	// Manager to employee leave transaction 
	@PutMapping("/updateStatus-manager/{leaveId}/{status}") 
	public ResponseEntity<String> updateLeaveStatus1(@PathVariable Long leaveId, @PathVariable int status) throws MessagingException { 
		
		/* applyLeaveService.updateStatus(leaveEmployeeId, status); 
		
		// Fetch employee name 
		String employeeName = applyLeaveService.getEmployeeName(leaveEmployeeId); 
		
		// Fetch employee email 
		String employeeEmail = applyLeaveService.getEmployeeEmail(employeeName); 
		
		String managerEmail = (String) httpSession.getAttribute("managerLoggedInEmail");  
		
	
		
		// Determine status message 
		String statusMessage = (status == 1)? "approved" : "rejected"; 
		String subject = "Leave Request : " + statusMessage; 
		String body = "<p>Dear " + employeeName + ",</p>" 
					  + "<p>Your leave request has been <b> " + statusMessage + "</b>.</p>" 
					  +"<p>Best regards,</p>" 
					  +"<p>Management Team</p>"; 
		
		emailService.sendEmail(employeeEmail, managerEmail, subject, body); 
		
		System.out.println("Manager to employee mail transaction : " + "\nMail From : " + managerEmail + "\nMail To : " + employeeEmail + "\nMail successfully sent"); 
		
		return "Leave status updated and email sent."; */ 
		
		try { 
			applyLeaveService.updateLeaveStatus(leaveId, status); 
			return ResponseEntity.ok("Status updated and email sent"); 
		} 
		catch(Exception exception) { 
			exception.printStackTrace(); 
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update."); 
		} 
	} 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* @GetMapping("/analyst") 
	public String getLeaveAnalyst(Model model) { 
		List<ApplyLeave> analystLeave = applyLeaveService.getLeaveRequestByTeam("Analyst"); 
		model.addAttribute("analystLeave", analystLeave); 
		return "Mactionrequired"; 
	} 
	@GetMapping("/developer") 
	public String getLeaveDeveloper(Model model) { 
		List<ApplyLeave> developerLeave = applyLeaveService.getLeaveRequestByTeam("Developer");  
		model.addAttribute("developerLeave", developerLeave); 
		return "Mactionrequired"; 
	} 
	
	
	@GetMapping("/tester") 
	public String getLeaveTester(Model model) { 
		List<ApplyLeave> testerLeave = applyLeaveService.getLeaveRequestByTeam("Tester"); 
		model.addAttribute("testerLeave", testerLeave); 
		return "Mactionrequired"; 
	} 
	
	@GetMapping("/devops") 
	public String getLeaveDevops(Model model) { 
		List<ApplyLeave> devopsLeave = applyLeaveService.getLeaveRequestByTeam("Devops"); 
		model.addAttribute("devopsLeave", devopsLeave); 
		return "Mactionrequired"; 
	} 
	
	@GetMapping("/cloud") 
	public String getLeaveCloud(Model model) { 
		List<ApplyLeave> cloudLeave = applyLeaveService.getLeaveRequestByTeam("Cloud"); 
		model.addAttribute("cloudLeave", cloudLeave); 
		return "Mactionrequired"; 
	} 
	
	@GetMapping("/hr") 
	public String getLeaveHR(Model model) { 
		List<ApplyLeave> hrLeave = applyLeaveService.getLeaveRequestByTeam("HR"); 
		model.addAttribute("hrLeave", hrLeave); 
		return "Mactionrequired"; 
	} 
	
	@GetMapping("/marketing") 
	public String getLeaveMarketing(Model model) { 
		List<ApplyLeave> marketingLeave = applyLeaveService.getLeaveRequestByTeam("Marketing"); 
		model.addAttribute("marketingLeave", marketingLeave); 
		return "Mactionrequired"; 
	} 
	
	
	@GetMapping("/action") 
	public ResponseEntity<List<Map<String, Object>>> getAllLeaveRequests(HttpSession session){ 
		
		String employeeName = (String) session.getAttribute("employeeName"); 
		
		if(employeeName == null) { 
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); 
		} 
		
		String query = "SELECT l FROM ApplyLeave l"; 
		List<ApplyLeave> leaveRequests = entityManager.createQuery(query, ApplyLeave.class).getResultList(); 
		
		
		List<Map<String, Object>> response = new ArrayList<>(); 
		for(ApplyLeave leave : leaveRequests) { 
			Map<String, Object> map = new HashMap<>(); 
			map.put("employeeName", employeeName); // Get name from session 
			map.put("leaveType", leave.getLeaveType()); 
			map.put("leaveFrom", leave.getLeaveFrom()); 
			map.put("leaveTo", leave.getLeaveTo()); 
			map.put("description", leave.getDescription()); 
			map.put("status", leave.getStatus()); 
			
			response.add(map); 
			
		} 
			
		return ResponseEntity.ok(response); 	
	} 
	
	@PutMapping("/updateStatus/{id}/{status}") 
	public ResponseEntity<String> updateLeaveStatus(@PathVariable Long id, @PathVariable String status){ 
		applyLeaveService.updateStatus(id,  status); 
		return ResponseEntity.ok("Leave status updated successfully"); 
	} */ 
		
} 
