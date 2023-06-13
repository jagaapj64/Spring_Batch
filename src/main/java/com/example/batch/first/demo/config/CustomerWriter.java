package com.example.batch.first.demo.config;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.batch.first.demo.model.CustomerProduct;
import com.example.batch.first.demo.repository.CustomerProductRepository;

@Component
public class CustomerWriter implements ItemWriter<CustomerProduct> {


    @Autowired
	private CustomerProductRepository customerProductRepository;
    
 
	@Override
	public void write(List<? extends CustomerProduct> items) throws Exception {
		 System.out.println("Thread Name : -"+Thread.currentThread().getName());
	        customerProductRepository.saveAll(items);		
	}

	
}
