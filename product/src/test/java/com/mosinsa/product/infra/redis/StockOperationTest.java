package com.mosinsa.product.infra.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockOperationTest {

	@Autowired
	StockOperation operation;

	@Test
	void decreaseAndGet() {

		String key1 = "key1";
		String key2 = "key2";

		operation.set(key1, 20);
		operation.set(key2, 20);

		List<StockOperand> stockOperands = List.of(new StockOperand(key1, 5L), new StockOperand(key2, 10L));
		assertThat(operation.decreaseAndGet(stockOperands)).containsExactly(15L,10L);
		assertThat(operation.decreaseAndGet(stockOperands)).containsExactly(10L,0L);
		assertThat(operation.decreaseAndGet(stockOperands)).containsExactly(5L,-10L);

	}

	@Test
	void increaseAndGet() {
		String key1 = "key1";
		String key2 = "key2";

		operation.set(key1, 20);
		operation.set(key2, 20);

		List<StockOperand> stockOperands = List.of(new StockOperand(key1, 10L), new StockOperand(key2, 10L));
		assertThat(operation.increaseAndGet(stockOperands)).containsExactly(30L,30L);
		assertThat(operation.increaseAndGet(stockOperands)).containsExactly(40L,40L);
	}

	@Test
	void setAndGet() {
		String key = UUID.randomUUID().toString();
		assertThat(operation.get(key)).isZero();
		operation.set(key, 10);

		assertThat(operation.get(key)).isEqualTo(10);

	}

	@Test
	void getDefault(){
		long defaultValue = 3L;
		assertThat(operation.get(UUID.randomUUID().toString(), defaultValue)).isEqualTo(defaultValue);
	}

}