package com.mosinsa.product.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockHistoryTest {
	@Autowired
	StockHistoryRepository repository;

	@Test
	void equalsAndHashcode() {
		StockHistory of = StockHistory.of("orderNum", "memberId", "productId", 10, StockHistoryType.PLUS);
		StockHistory of1 = StockHistory.of("orderNum", "memberId", "productId", 10, StockHistoryType.MINUS);
		repository.saveAll(List.of(of, of1));


		StockHistory a = repository.findById(of.getId()).get();
		StockHistory b = repository.findById(of.getId()).get();
		StockHistory c = repository.findById(of1.getId()).get();
		StockHistory protectedConstructor = new StockHistory();

		boolean b1 = EqualsAndHashcodeUtils.equalsAndHashcode(a, b, protectedConstructor, c);
		assertThat(b1).isTrue();
	}
}