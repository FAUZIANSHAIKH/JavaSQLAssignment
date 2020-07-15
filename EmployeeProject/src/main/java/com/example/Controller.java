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
		List<Employee> empData = service.getEmployeeByPlace(place);
		List<Employee> data = new ArrayList<>();
		Iterator<Employee> empIterator = empData.iterator();
		while (empIterator.hasNext()) {
			Employee currentEmp = empIterator.next();
			double salary = currentEmp.getSalary();
			salary = salary + (salary * (percentage / 100));
			System.out.println("SALARY---------------------" + salary);
			currentEmp.setSalary(salary);
			System.out.println("CURRENT_EMPLOYEE-----------------" + currentEmp);
			System.out.println("UPDATE---------------------------------");
			System.out.println(service.updateEmployee(currentEmp));
			data.add(service.updateEmployee(currentEmp));
//			empIterator.next();
		}
		System.out.println("DATA-------------------------" + data);
		return data;
	}

	@GetMapping("/employees/totalSalary/{place}")
	public Double findTotalSalaryByPlace(@PathVariable String place) {
		List<Employee> empData = service.getEmployeeByPlace(place);
		double totalSalary = 0;
		Iterator<Employee> empIterator = empData.iterator();
		while (empIterator.hasNext()) {
			Employee currentEmp = empIterator.next();
			double salary = currentEmp.getSalary();
			totalSalary += salary;
		}
		System.out.println("DATA-------------------------" + totalSalary);
		return totalSalary;
	}

	@GetMapping("/employees/totalSalary/supervisor/{supervisorId}")
	public Double findTotalSalaryBySupervisorId(@PathVariable String supervisorId) {
		List<Employee> empData = service.getEmployeeBySupervisorId(supervisorId);
		double totalSalary = 0;
		Iterator<Employee> empIterator = empData.iterator();
		while (empIterator.hasNext()) {
			Employee currentEmp = empIterator.next();
			double salary = currentEmp.getSalary();
			totalSalary += salary;
		}
		System.out.println("DATA-------------------------" + totalSalary);
		return totalSalary;
	}

	@GetMapping("/employees/salaryRange/{title}")
	public List<Double> findSalaryRangeByTitle(@PathVariable String title) {
		List<Employee> empData = service.getEmployeeByTitle(title);
		List<Double> data = new ArrayList<>();
		double minSalary = Double.POSITIVE_INFINITY;
		double maxSalary = 0;
		if (empData.isEmpty()) {
			return null;
		}
		Iterator<Employee> empIterator = empData.iterator();
		while (empIterator.hasNext()) {
			Employee emp = empIterator.next();
			double salary = emp.getSalary();
			minSalary = Math.min(minSalary, salary);
			maxSalary = Math.max(maxSalary, salary);

		}
		data.add(minSalary);
		data.add(maxSalary);
		return data;
	}

//	@GetMapping("/employees/supervisees/{supervisorId}")
//	public List<String> findSuperviseesBySupervisorId(@PathVariable String supervisorId) {
//		List<Employee> empData = (List<Employee>) service.getEmployeeBySupervisorId(supervisorId);
//		List<String> data = new ArrayList<>();
//		if (empData.isEmpty()) {
//			return null;
//		}
//		Iterator<Employee> empIterator = empData.iterator();
//		while (empIterator.hasNext()) {
//			Employee emp = empIterator.next();
//			if(emp.getEmpId().equals(service.getEmployeeBySupervisorId(emp.getSupervisorId())))	{
//				data.add(supervisorId);
//				System.out.println("DATA============"+data);
//			}
//		}
//		System.out.println("QUEUE-----------------"+data);
//		return data;
//	}


	//SELECT e.emp_id 'Emp_Id', e.last_name 'Employee', m.emp_id 'Mgr_Id', m.last_name 'Manager'
	//FROM employee e join employee m ON (e.supervisor_id = m.emp_Id);
}
