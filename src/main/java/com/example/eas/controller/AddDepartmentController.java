package com.example.eas.controller; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.eas.entity.AddDepartment;
import com.example.eas.service.AddDepartmentService;

@Controller 
@RequestMapping("/department") 
public class AddDepartmentController { 
	
	@Autowired 
	private AddDepartmentService addDepartmentService; 

	@GetMapping("/show-department-form") 
	public String showDepartmentForm(Model model) { 
		model.addAttribute("addDepartment", new AddDepartment()); 
		return "Madddepartment"; 
	} 
	
	@PostMapping("/save-departments") 
	public String saveDepartment(@ModelAttribute AddDepartment addDepartment) { 	
		addDepartmentService.saveDepartment(addDepartment); 
		return "redirect:/department/show-department-form"; 
	} 
	
	@GetMapping("/departments-list") 
	public String showDepartmentList(Model model) { 
		List<AddDepartment> department = addDepartmentService.getAllDepartments(); 
		model.addAttribute("departments", department); 
		return "Mmanagedepartment"; 
	} 
	
	@PostMapping("/departments/delete")
    public String deleteDepartment(@RequestParam("depCode") String depCode) {
        addDepartmentService.deleteByDepCode(depCode);
        return "redirect:/department/departments-list";
    }
	
}


