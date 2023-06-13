package com.example.batch.first.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch.first.demo.model.CustomerProduct;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, String> {

}
