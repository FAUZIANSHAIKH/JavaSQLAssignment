package com.example.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.model.Employee;
import com.example.repository.EmployeeDTO;

import javax.transaction.Transactional;

@Service
public class EmployeeService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private EmployeeDTO edto;
	
	String line="";
	public void saveEmployeeData() {
		try {
			BufferedReader br=new BufferedReader(new FileReader("src/main/resources/employee.csv"));
			while((line=br.readLine())!=null) {
				String [] data=line.split(",");
				Employee e=new Employee();
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

	public List<Employee> getEmployees(){
		return (List<Employee>) edto.findAll();
	}

	public List<Employee> getEmployeeByPlace(String place){
		System.out.println("PLACE-------"+place);
		return (List<Employee>) edto.findByPlace(place);
	}


	public List<Employee> getEmployeeBySupervisorId(String supervisorId){
		System.out.println("SupervisorId-------"+supervisorId);
		return (List<Employee>) edto.findBySupervisorId(supervisorId);
	}

	public Employee getEmployeeByEmpId(String empId){
		return edto.findByEmpId(empId);
	}

	public List<Employee> getEmployeeByTitle(String title){
		return (List<Employee>) edto.findByTitle(title);
	}

	public Employee updateEmployee(Employee employee){
		return edto.save(employee);
	}

//	@Transactional
//	public void getHirerchy(int id){
//		result = String query = "WITH EmpCTE AS(Select empId,firstName,supervisorId From employee " +
//				"Where empId = ? " +
//				"UNION ALL " +
//				"Select employee.empId , employee.firstName, employee.supervisorId From employee " +
//				"JOIN " +
//				"EmpCTE ON employee.empId = EmpCTE.supervisorId) " +
//				"Select E1.firstName, ISNULL(E2.firstName, 'No Boss') as ManagerName " +
//				"From EmpCTE E1 " +
//				"LEFT Join " +
//				"EmpCTE E2 ON E1.supervisorId = E2.empId";
//		jdbcTemplate.query(query,id);
//
//	}
}
