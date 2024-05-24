package com.mosinsa.product.domain.product;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LikesIdTest {

	@Test
	void idCreateFail(){
		assertThrows(IllegalArgumentException.class, () -> LikesId.of(null));
		assertThrows(IllegalArgumentException.class, () -> LikesId.of(""));
	}

	@Test
	void idCreateSuccess(){
		String value = "id";
		LikesId id = LikesId.of(value);
		assertThat(id.getId()).isEqualTo(value);
	}


	@Test
	void idEqualsAndHashCode(){
		String value = "id";
		LikesId idA = LikesId.of(value);
		LikesId idB = LikesId.of(value);

		assertThat(idA).isEqualTo(idB).hasSameHashCodeAs(idB);
	}
}