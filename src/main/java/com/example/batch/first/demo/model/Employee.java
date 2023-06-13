package com.example.batch.first.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="employee_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	@Column(name="emp_id")
	private int empId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone_Number")
	private String phoneNumber;
	
	@Column(name="hire_date")
	private String hireDate;
	
	@Column(name="job_Id")
	private String jobId;
	
	@Column(name="salary")
	private Double salary;
	
	@Column(name="manager_id")
	private Double managerId;
	
	@Column(name="department_id")
	private Double departmentId;
	
}
