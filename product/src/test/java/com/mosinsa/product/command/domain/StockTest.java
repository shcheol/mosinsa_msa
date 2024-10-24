package com.mosinsa.product.command.domain;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StockTest {

	@Test
	void stockCreate(){

		Stock stock = Stock.of(10);
		assertThat(stock.getId()).isNotEmpty();
		assertThat(stock.getTotal()).isEqualTo(10);
		assertThat(stock.getStatus()).isEqualTo(StockStatus.ON);
	}

	@Test
	void idEqualsAndHashCode() {
		long value = 10L;
		Stock idA = Stock.of(value);
		Stock protectedConstructor = new Stock();

		assertThat(idA).isEqualTo(idA).hasSameHashCodeAs(idA)
				.isNotEqualTo(null).isNotEqualTo(new TestClass())
				.doesNotHaveSameHashCodeAs(protectedConstructor);
		assertThat(protectedConstructor.hashCode()).isZero();

	}

}