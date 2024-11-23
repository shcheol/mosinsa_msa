package com.mosinsa.product.command.application;

import com.mosinsa.category.application.CategoryService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProductApplicationObjectFactory {

	@Bean
	public CategoryService categoryService(){
		return new CategoryServiceStub();
	}

	@Bean
	public StockPort stockPort(){
		return new StockPortStub();
	}
}
