package com.example.batch.first.demo.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.batch.first.demo.model.CustomerProduct;

public class CustomerProductProcessor implements ItemProcessor<CustomerProduct, CustomerProduct> {

	@Override
	public CustomerProduct process(CustomerProduct item) throws Exception {
		return item;
	}

}
