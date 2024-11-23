package com.mosinsa.product.command.domain;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockTest {

	@Test
	void stockCreate(){

		Stock stock = Stock.of(10);
		assertThat(stock.getTotal()).isEqualTo(10);
		assertThat(stock.getStatus()).isEqualTo(StockStatus.ON);
	}

	@Test
	void stockEx(){

		assertThrows(InvalidStockException.class, () -> Stock.of(-1));
	}

}