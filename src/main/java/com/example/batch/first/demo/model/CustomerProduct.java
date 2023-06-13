package com.example.batch.first.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProduct {

	@Id
	@Column(name="index_id")
	private int index;
	
	@Column(name="organization_id")
	private String organizationId;
	
	@Column(name ="name")
	private String name;
	
	@Column(name="website")
	private String website;
	
	@Column(name="country")
	private String country;
	
	@Column(name="description")
	private String description;
	
	@Column(name="founded")
	private Integer founded;
	
	@Column(name="industry")
	private String industry;
	
	@Column(name="number_of_employees")
	private Integer numberOfEmployees;
	
	
	
}
