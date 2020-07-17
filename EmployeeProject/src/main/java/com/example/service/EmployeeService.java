package com.example.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.model.Employee;
import com.example.repository.EmployeeDTO;

import javax.transaction.Transactional;

@Service
public class EmployeeService<data> {

	@Autowired
	private EmployeeDTO edto;

	String line = "";
	public static List<Employee> data = new ArrayList<>();

	public void saveEmployeeData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/employee.csv"));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				Employee e = new Employee();
				e.setEmpId(data[0]);
				e.setFirstName(data[1]);
				e.setLastName(data[2]);
				e.setGender(data[3]);
				e.setEmail(data[4]);
				e.setSalary(Double.parseDouble(data[5]));
				e.setPlace(data[6]);
				e.setTitle(data[7]);
				e.setSupervisorId(data[8]);
				edto.save(e);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Employee> getEmployees() {
		return (List<Employee>) edto.findAll();
	}

	public List<Employee> getEmployeeByPlace(String place) {
		System.out.println("PLACE-------" + place);
		return (List<Employee>) edto.findByPlace(place);
	}


	public List<Employee> getEmployeeBySupervisorId(String supervisorId) {
		System.out.println("SupervisorId-------" + supervisorId);
		return (List<Employee>) edto.findBySupervisorId(supervisorId);
	}

	public Employee getEmployeeByEmpId(String empId) {
		return edto.findByEmpId(empId);
	}

	public List<Employee> getEmployeeByTitle(String title) {
		return (List<Employee>) edto.findByTitle(title);
	}

	public Employee updateEmployee(Employee employee) {
		return edto.save(employee);
	}

	public List<Employee> getSalary(String place, Double percentage) {
		List<Employee> empData = getEmployeeByPlace(place);
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
			System.out.println(updateEmployee(currentEmp));
			data.add(updateEmployee(currentEmp));
//			empIterator.next();
		}
		return data;
	}

	public Double getTotalSalaryByPlace(String place) {
		List<Employee> empData = getEmployeeByPlace(place);
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

	public Double getTotalSalaryBySupervisorId(String supervisorId) {
		List<Employee> empData = getEmployeeBySupervisorId(supervisorId);
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

	public List<Double> getSalaryRange(String title) {
		List<Employee> empData = getEmployeeByTitle(title);
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

	public void getHirarchy(List<Employee> supervisorId) {
		System.out.println("SUPERVISOR ------------------------" + supervisorId);
		//Employee empData = getEmployeeByEmpId(supervisorId);
		for (Employee e : supervisorId){
			data.add(e);
			System.out.println("Data-----------"+e);
		}
		if (supervisorId.size() == 0) {
			return;
		}
		System.out.println("EMPDATA----------------------" + data);
		System.out.println("EMPDataId------------------------------------");
		Iterator<Employee> empIterator = supervisorId.iterator();
		while (empIterator.hasNext()) {
			Employee emp = empIterator.next();
			System.out.println("----------------------------"+getEmployeeBySupervisorId(emp.getEmpId()));
			if (getEmployeeBySupervisorId(emp.getEmpId()) != null) {
//				System.out.println("DATA============" + data);
				getHirarchy(getEmployeeBySupervisorId(emp.getEmpId()));
			}
			return;

		}
	}
}
