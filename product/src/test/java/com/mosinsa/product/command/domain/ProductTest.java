package com.mosinsa.product.command.domain;

import com.mosinsa.category.domain.Category;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTest {

	@Autowired
	ProductRepository repository;

	@Test
	void invalidMoneyException() {
		Category of = Category.of("category1");
		assertThrows(InvalidMoneyException.class, () -> Product.create("name", 0, of, 10));
	}

	@Test
	void invalidStockException() {
		Category of = Category.of("category1");
		assertThrows(InvalidStockException.class, () -> Product.create("name", 100, of, -1));
	}

	@Test
	void equalsAndHashCode() {
		Product productA = repository.save(Product.create("name", 100, Category.of("category1"), 10));
		Product productB = repository.findById(productA.getId()).get();

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(productA, productB, new Product(), Product.create("", 100, Category.of("category1"), 10));
		assertThat(b).isTrue();
	}

}