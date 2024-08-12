package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.query.dto.PromotionSummary;
import com.mosinsa.promotion.query.dto.PromotionSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomPromotionRepositoryImplTest {

	@Autowired
	private PromotionRepository repository;

	@Test
	void proceedingPromotions() {

		PromotionSearchCondition condition = new PromotionSearchCondition(true, "title1", LocalDateTime.of(2023, 10, 31, 0, 0));
		PageRequest of = PageRequest.of(0, 3);
		List<PromotionSummary> promotions = repository.findPromotionsByCondition(condition, of).getContent();
		assertThat(promotions).hasSize(1);
	}

	@Test
	void allPromotions() {

		PromotionSearchCondition condition = new PromotionSearchCondition(false, "title1", LocalDateTime.of(2023, 10, 31, 0, 0));
		PageRequest of = PageRequest.of(0, 3);
		Page<PromotionSummary> promotions = repository.findPromotionsByCondition(condition, of);

		assertThat(promotions.getContent()).hasSize(3);
	}
}