package com.mosinsa.product.query;

import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OptionCombinationServiceTest {

	@Autowired
	ProductRepository repository;

	@Autowired
	OptionCombinationService optionCombinationService;

//	@Test
	void getCombinationMap() {

		Product product = repository.findById(ProductId.of("01be3593")).get();
		optionCombinationService.getCombinationMap(product);

	}
}