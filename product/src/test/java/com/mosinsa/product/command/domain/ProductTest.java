package com.mosinsa.product.command.domain;

import com.mosinsa.category.domain.Category;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTest {

	@Autowired
	ProductRepository repository;

	@Test
	void equalsAndHashCode() {
		Product productA = repository.save(Product.of("name", 100, Category.of("category1")));
		Product productB = repository.findById(productA.getId()).get();

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(productA, productB, new Product(), Product.of("", 100, Category.of("category1")));
		assertThat(b).isTrue();
	}

}