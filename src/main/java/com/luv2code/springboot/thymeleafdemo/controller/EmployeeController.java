package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	/**
	//load employee data
	
	private List<Employee> theEmployees;
	**/
	/**
	@PostConstruct
	private void loadData() {
		// create employees
		Employee emp1 = new Employee(1, "Leslie", "Andrews", "leslie@luv2code.com");
		Employee emp2 = new Employee(2, "Emma", "Baumgarten", "emma@luv2code.com");
		Employee emp3 = new Employee(3, "Avani", "Gupta", "avani@luv2code.com");
		//create the list
		theSEmployees = new ArrayList<>();
		
		//add to the list
		theEmployees.add(emp1);
		theEmployees.add(emp2);
		theEmployees.add(emp3);
	}
	**/
	private EmployeeService employeeService;
	
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	//add mapping for "/list"
	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		
		//Get employees from the db
		List<Employee> theEmployees = employeeService.findAll();
		
		//add to the spring model
		theModel.addAttribute("employees", theEmployees);
		return "employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
		public String showFormForAdd(Model theModel) {
		
		//create model attribute to bind form data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee);
		
		return "employees/employee-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		//get the employee from the service 
		Employee theEmployee = employeeService.findById(theId);
		
		//set the employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		//send over to our form
		return "employees/employee-form";
	}
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
    	//save the employee
    	employeeService.save(theEmployee);
    	
    	//use a redirect to prevent duplicate resubmission
    	return "redirect:/employees/list";
    }
    
    @PostMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		// delete the employee
		employeeService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/employees/list";
    }
}
