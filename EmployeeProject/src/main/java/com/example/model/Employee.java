package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//lombok annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Employee {

	@Id
	@GeneratedValue
	private int id;

	private String empId;
	private String firstName;
	private String lastName;
	private String gender;

	private String email;
	private double salary;
	private String place;
	private String title;
	private String supervisorId;

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}


	//when lombok annotations are used,there is no need of setters and getters
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String data) {
		this.empId = data;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", empId='" + empId + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", gender='" + gender + '\'' +
				", email='" + email + '\'' +
				", salary='" + salary + '\'' +
				", place='" + place + '\'' +
				", title='" + title + '\'' +
				", supervisorId='" + supervisorId + '\'' +
				'}';
	}
}

