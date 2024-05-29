package com.mosinsa.product.domain.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductIdTest {

	@Test
	void idCreateFail(){
		assertThrows(IllegalArgumentException.class, () -> ProductId.of(null));
		assertThrows(IllegalArgumentException.class, () -> ProductId.of(""));
	}

	@Test
	void idCreateSuccess(){
		String value = "id";
		ProductId id = ProductId.of(value);
		assertThat(id.getId()).isEqualTo(value);
	}


	@Test
	void idEqualsAndHashCode(){
		String value = "id";
		ProductId idA = ProductId.of(value);
		ProductId idB = ProductId.of(value);

		assertThat(idA).isEqualTo(idA).isEqualTo(idB).hasSameHashCodeAs(idB)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		ProductId idC = ProductId.of("idxx");
		assertThat(idA).isNotEqualTo(idC).doesNotHaveSameHashCodeAs(idC);
	}
	static class TestClass{

	}

}