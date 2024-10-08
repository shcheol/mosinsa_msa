package com.mosinsa.product.command.domain;

import com.mosinsa.category.Category;
import com.mosinsa.code.TestClass;
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
    void invalidMoneyException(){
		Category of = Category.of("category1");
		assertThrows(InvalidMoneyException.class, () -> Product.create("name", 0, of, 10));
    }

	@Test
	void invalidStockException(){
		Category of = Category.of("category1");
		assertThrows(IllegalArgumentException.class, () -> Product.create("name", 100, of, 0));
	}

	@Test
	void equalsAndHashCode(){
		Product productA = Product
				.create("name", 100, Category.of("category1"), 10);

		assertThat(productA).isEqualTo(productA)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		Product protectedConstructor = new Product();
		assertThat(protectedConstructor).isNotEqualTo(productA);
	}

}