package com.example.batch.first.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch.first.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
