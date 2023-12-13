package com.mosinsa.product.domain.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

	@Test
	void equals() {
		ProductId test = ProductId.of("test");
		assertThat(test).isEqualTo(test)
				.isNotEqualTo(null)
				.isNotEqualTo(new TestClass());

		ProductId test2 = ProductId.of("test");
		assertThat(test).isEqualTo(test2);
	}

	static class TestClass {
	}


}