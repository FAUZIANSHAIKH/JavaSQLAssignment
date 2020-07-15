package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDTO extends JpaRepository<Employee,Integer> {
    List<Employee> findByPlace(String place);
    Employee findByEmpId(String empId);
    List<Employee> findByTitle(String title);
    List<Employee> findBySupervisorId(String supervisorId);

}
