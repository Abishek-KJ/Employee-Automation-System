package com.example.eas.controller; 

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.eas.entity.ManagerSignup;
import com.example.eas.service.ManageManagerService;

@Controller 
@RequestMapping("/admin") 
public class ManageManagerController { 
	
	private final ManageManagerService manageManagerService; 
	
	public ManageManagerController(ManageManagerService manageManagerService) { 
		this.manageManagerService = manageManagerService; 
	} 
	
	@GetMapping("/manage-managers") 
	public String getAllManagersList(Model model) { 
		
		List<ManagerSignup> allManagers = manageManagerService.getAllManagers(); 
		
		model.addAttribute("managers", allManagers); 
		
		return "Amanage"; 
	} 
	
	@GetMapping("/updateManager") 
	public String showUpdateForm(@RequestParam String name, @RequestParam String email, Model model) { 
		model.addAttribute("name", name); 
		model.addAttribute("email", email); 
		ManagerSignup managerSignup = manageManagerService.getManagerByEmail(email); 
		if(managerSignup != null) { 
			BigDecimal ctcLpa = managerSignup.getManagerCTC().divide(BigDecimal.valueOf(100000), 2, RoundingMode.HALF_UP); 
			model.addAttribute("ctcLpa", ctcLpa);
			model.addAttribute("experience", managerSignup.getManagerPreviousExperience()); 
		} 
		
		return "Aeditmanager"; 
	} 
	
	@PostMapping("/updateManagerDetails") 
	public String updateManagerDetails(@RequestParam String name, 
										@RequestParam String email,
										@RequestParam double ctcLpa, 
										@RequestParam double experience, 
										Model model){ 
		
		long ctcInRupees = (long) (ctcLpa * 100000); 
		
		manageManagerService.updateCtcAndExperienceByEmail(email, ctcInRupees, experience); 
			
		model.addAttribute("message", "Manager updated successfully."); 
		
		return "redirect:/admin/manage-managers"; 
	} 
	
	
	@PostMapping("/update-manager") 
	public String updateManager(@ModelAttribute ManagerSignup manager, RedirectAttributes redirectAttributes) { 
		manageManagerService.updateManagerInformation(manager.getManagerMailId(), manager.getManagerCTC(), manager.getManagerPreviousExperience()); 
		redirectAttributes.addFlashAttribute("message", "Manager updated successfully."); 
		return "redirect:/admin/manage-managers"; 
	} 
	
	 @PostMapping("/deleteManager")
	    public String deleteManager(@RequestParam("email") String email) {
	        manageManagerService.deleteByManagerMailId(email);
	        return "redirect:/admin/manage-managers";
	    }
} 



