package com.mosinsa.product.command.domain;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
		assertThat(a).isEqualTo(a).isEqualTo(b).hasSameHashCodeAs(b)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		StockHistory c = repository.findById(of1.getId()).get();
		assertThat(a).isNotEqualTo(c).doesNotHaveSameHashCodeAs(c);

		StockHistory protectedConstructor = new StockHistory();
		assertThat(protectedConstructor).isNotEqualTo(c);
		assertThat(protectedConstructor.hashCode()).isZero();
	}
}