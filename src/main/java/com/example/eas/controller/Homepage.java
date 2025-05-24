package com.example.eas.controller; 

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.eas.dto.OrganizationDetailsConfigDTO;
import com.example.eas.entity.BankDetails;
import com.example.eas.entity.ManagerApplyLeave;
import com.example.eas.entity.ManagerSignup;
import com.example.eas.entity.OrganizationDetails;

import jakarta.servlet.http.HttpSession;

@Controller
public class Homepage { 
	
	/* @GetMapping("/index") 
	public String index() { 
		return "index"; 
	} */  
	
	
	@GetMapping("/footer") 
	public String footer() { 
		return "footer"; 
	} 
	
	
	//General Page 
	
	@GetMapping("/index") 
	public String index() { 
		return "redirect:/index.html"; 
	} 
	
	@GetMapping("/welcome") 
	public String welcome(HttpSession session, Model model) { 
		session.invalidate(); 
		model.addAttribute("managerSignup", new ManagerSignup()); 
		return "welcome"; 
	} 
	
	
	// Employee Login Webpages 
	
	
	
	@GetMapping("/eDashboard") 
	public String eDashboard() { 
		return "eDashboard"; 
	} 


	@GetMapping("/Eapplyleave") 
	public String eApplyLeave(){ 
		return "Eapplyleave"; 
	} 
	
	@GetMapping("/Eleavehistory") 
	public String eLeaveHistory() { 
		return "eLeaveHistory"; 
	} 
	
	
	@GetMapping("/Ecalendar") 
	public String eCalendar() { 
		return "Ecalendar"; 
	} 
	
	@GetMapping("/Echangepassword") 
	public String eChangePassword() { 
		return "Echangepassword"; 
	} 
	
	@GetMapping("/Eprofile") 
	public String eProfile() { 
		return "Eprofile"; 
	} 
	
	@GetMapping("/Epayroll") 
	public String ePayroll() { 
		return "Epayroll"; 
	} 

	
	//Manager Login Webpages 
	
	@GetMapping("/mDashboard") 
	public String mDashboard() { 
		return "mDashboard"; 
	} 
	
	@GetMapping("/Mchangepassword") 
	public String mChangePassword() { 
		return "Mchangepassword"; 
	} 
	
//	@GetMapping("/Maddemployee") 
//	public String mAddEmployee() { 
//		return "Maddemployee"; 
//	} 
//	
	@GetMapping("/Mtookaction") 
	public String mTookAction() { 
		return "Mtookaction"; 
	} 
	
	@GetMapping("/Mactionrequired") 
	public String mActionRequired() { 
		return "MactionRequired"; 
	} 
	
	@GetMapping("/Madddepartment") 	
	public String mAddDepartment() { 
		return "Madddepartment"; 
	} 
	
	@GetMapping("/Mmanageemployee") 
	public String mManageEmployee() { 
		return "Mmanageemployee"; 
	}  
	
	@GetMapping("/Meditempinformation") 
	public String mEditEmpInformation() { 
		return "Meditempinformation"; 
	} 
	
	@GetMapping("/Mapplyleave") 
	public String mApplyLeave(Model model) { 
		model.addAttribute("managerApplyLeave", new ManagerApplyLeave()); 
		return "Mapplyleave"; 
	} 
	
	@GetMapping("/Mpayroll") 
	public String mPayroll() { 
		return "Mpayroll"; 
	} 

	
	// Administrator Login Webpages 
	
	@GetMapping("/aPage") 
	public String  aPage() { 
		return "/aPage"; 
	} 
	
	@GetMapping("/Asalary") 
	public String aSalary() { 
		return "/aSalary"; 
	} 
	
	@GetMapping("/Aselectbank") 
	public String aSelectBank() { 
		return "/Aselectbank"; 
	} 
	
	
	@GetMapping("/Adirectdeposit") 
	public String aDirectDeposit(Model model) { 
	    model.addAttribute("bankDetails", new BankDetails());
		return "Adirectdeposit"; 
	}  
	
	@GetMapping("/AdirectdepositHDFC") 
	public String aDirectDepositHDFC(Model model) { 
		model.addAttribute("bankDetails", new BankDetails());
		return "AdirectdepositHDFC"; 
	} 
	
	@GetMapping("/AdirectdepositIDFC") 
	public String aDirectDepositIDFC(Model model) { 
		model.addAttribute("bankDetails", new BankDetails());
		return "AdirectdepositIDFC"; 
	} 
	
	@GetMapping("/Aconfiguration") 
	public String aConfiguration(Model model) { 
		model.addAttribute("organization", new OrganizationDetailsConfigDTO()); 
		return "/Aconfiguration"; 
	} 
	
	@GetMapping("/Aactionrequired") 
	public String aActionRequired() { 
		return "Aactionrequired"; 
	} 
	
	@GetMapping("/Aactiontaken") 
	public String aActionTaken(Model model) { 
		model.addAttribute("managerApplyLeave", new ManagerApplyLeave()); 
		return "Aactiontaken"; 
	} 
	
	@GetMapping("/Amanage") 
	public String aManage(Model model) { 
		model.addAttribute("manage", new ManagerSignup());  
 		return "/Amanage"; 
	} 
	
	@GetMapping("/Aeditmanager") 
	public String aEditManager() { 
		return "Aeditmanager"; 
	} 

} 


