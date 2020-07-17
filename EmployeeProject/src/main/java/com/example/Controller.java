package com.example;

import com.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import com.example.service.EmployeeService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class Controller {

	@Autowired
	private EmployeeService service;

	@RequestMapping(path = "feedEmployeeData")
	public void setDataInDB() {
		service.saveEmployeeData();
	}

	@GetMapping("/employees")
	public List<Employee> findAllEmployees() {
		return service.getEmployees();
	}

	@GetMapping("/employees/place/{place}")
	@Cacheable(value="employees",key="#place")
	public List<Employee> findEmployeeByPlace(@PathVariable(value= "place") String place) {
		System.out.println("Fetching Employee details from database");
		return service.getEmployeeByPlace(place);
	}

	@GetMapping("/employees/{empId}")
	public Employee findEmployeeByEmpId(@PathVariable String empId) {
		return service.getEmployeeByEmpId(empId);
	}

	@GetMapping("/employees/supervisorId/{supervisorId}")
	public List<Employee> findEmployeeBySupervisorId(@PathVariable String supervisorId) {
		return (List<Employee>) service.getEmployeeBySupervisorId(supervisorId);
	}

	@PutMapping("/employees/place/{place}/salary/{percentage}")
	@ResponseBody
	@CachePut(value="employees", key="#place")
	public List<Employee> findEmpByPlace(@PathVariable(value= "place") String place, @PathVariable Double percentage) {
		List<Employee> data = service.getSalary(place,percentage);
		return data;
	}

	@GetMapping("/employees/totalSalary/{place}")
	public Double findTotalSalaryByPlace(@PathVariable String place) {
		return service.getTotalSalaryByPlace(place);
	}

	@GetMapping("/employees/totalSalary/supervisor/{supervisorId}")
	public Double findTotalSalaryBySupervisorId(@PathVariable String supervisorId) {
		return service.getTotalSalaryBySupervisorId(supervisorId);
	}

	@GetMapping("/employees/salaryRange/{title}")
	public List<Double> findSalaryRangeByTitle(@PathVariable String title) {
		List<Double> data= service.getSalaryRange(title);
		return data;
	}

	@GetMapping("/employees/supervisees/{supervisorId}")
	public List<Employee> findSuperviseesBySupervisorId(@PathVariable String supervisorId) {
		EmployeeService.data.clear();
		List<Employee> emp = service.getEmployeeBySupervisorId(supervisorId);
		service.getHirarchy(emp);
		System.out.println("SuperVisor---------------"+EmployeeService.data);
		return EmployeeService.data;
	}


	//SELECT e.emp_id 'Emp_Id', e.last_name 'Employee', m.emp_id 'Mgr_Id', m.last_name 'Manager'
	//FROM employee e join employee m ON (e.supervisor_id = m.emp_Id);
}
