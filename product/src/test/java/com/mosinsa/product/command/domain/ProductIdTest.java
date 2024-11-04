package com.mosinsa.product.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
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
	void idEqualsAndHashCode() {
		String value = "id";
		ProductId idA = ProductId.of(value);
		ProductId idB = ProductId.of(value);
		ProductId idC = ProductId.of("idxx");
		ProductId protectedConstructor = new ProductId();

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(idA, idB, protectedConstructor, idC);
		assertThat(b).isTrue();
	}

}