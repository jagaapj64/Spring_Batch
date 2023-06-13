package com.example.batch.first.demo.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.batch.first.demo.model.Employee;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee emp) throws Exception {
		if (emp.getSalary() > 10000)
			return emp;

		else
			return null;

	}

}
