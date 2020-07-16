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

//	@GetMapping("/employees/supervisees/{supervisorId}")
//	public List<String> findSuperviseesBySupervisorId(@PathVariable String supervisorId) {
//		System.out.println("SUPERVISOR "+supervisorId);
//		Employee empData =  service.getEmployeeByEmpId(supervisorId);
//		System.out.println(empData.getEmpId());
//		List<String> data = new ArrayList<>();
////		if (empData.isEmpty()) {
////			return null;
////		}
////		Iterator<Employee> empIterator = empData.iterator();
////		while (empIterator.hasNext()) {
////			Employee emp = empIterator.next();
////			if(service.getEmployeeBySupervisorId(empData.getEmpId())!= null)	{
////
////				data.add(supervisorId);
////				findSuperviseesBySupervisorId(supervisorId);
////				System.out.println("DATA============"+data);
////			}
//
//		System.out.println("QUEUE-----------------"+data);
//		return data;
//	}


	//SELECT e.emp_id 'Emp_Id', e.last_name 'Employee', m.emp_id 'Mgr_Id', m.last_name 'Manager'
	//FROM employee e join employee m ON (e.supervisor_id = m.emp_Id);
}
