package com.mosinsa.product.infra.repository;

import com.mosinsa.code.TestClass;
import com.mosinsa.product.command.domain.StockHistory;
import com.mosinsa.product.command.domain.StockHistoryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockHistoryRepositoryTest {

	@Autowired
	StockHistoryRepository repository;

	@Test
	void save(){
		StockHistory of1 = StockHistory.of("orderNum", "memberId", "productId", 10, StockHistoryType.MINUS);
		StockHistory save = repository.save(of1);
		StockHistory find = repository.findById(save.getId()).get();

		assertThat(find.getId()).isEqualTo(save.getId());
		assertThat(find.getOrderNum()).isEqualTo("orderNum");
		assertThat(find.getMemberId()).isEqualTo("memberId");
		assertThat(find.getProductId()).isEqualTo("productId");
		assertThat(find.getQuantity()).isEqualTo(10);
		assertThat(find.getType()).isEqualTo(StockHistoryType.MINUS);
//		assertThat(find.getCreatedDate()).isBefore(LocalDateTime.now());

	}

	@Test
	void equalsAndHashcode() {
		StockHistory of = StockHistory.of("orderNum", "memberId", "productId", 10, StockHistoryType.PLUS);
		StockHistory of1 = StockHistory.of("orderNum", "memberId", "productId", 10, StockHistoryType.MINUS);
		repository.saveAll(List.of(of, of1));


		StockHistory a = repository.findById(of.getId()).get();
		StockHistory b = repository.findById(of.getId()).get();
		assertThat(a).isEqualTo(b).hasSameHashCodeAs(b)
				.isNotEqualTo(null).doesNotHaveSameHashCodeAs(new TestClass());

		StockHistory c = repository.findById(of1.getId()).get();
		assertThat(a).isNotEqualTo(c).doesNotHaveSameHashCodeAs(c);
	}
}