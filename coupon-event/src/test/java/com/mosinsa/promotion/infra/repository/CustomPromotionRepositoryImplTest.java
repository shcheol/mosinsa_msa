package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.domain.Promotion;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import com.querydsl.core.Tuple;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomPromotionRepositoryImplTest {

	@Autowired
	private PromotionRepository repository;

	@Test
	void findById(){
		PromotionId id = PromotionId.of("promotion1");
		System.out.println(id);
		Promotion promotion1 = repository.findById(id).get();
		Assertions.assertThat(promotion1.getPromotionId()).isEqualTo(id);
	}

	@Test
	void proceedingPromotions() {

		PromotionSearchCondition condition = new PromotionSearchCondition(true, "title1", LocalDateTime.of(2023, 10, 31, 0, 0));
		PageRequest of = PageRequest.of(0, 3);
		List<PromotionDto> promotions = repository.findPromotionsByCondition(condition, of).getContent();
		assertThat(promotions).hasSize(1);
	}

	@Test
	void allPromotions() {

		PromotionSearchCondition condition = new PromotionSearchCondition(false, "title1", LocalDateTime.of(2023, 10, 31, 0, 0));
		PageRequest of = PageRequest.of(0, 3);
		Page<PromotionDto> promotions = repository.findPromotionsByCondition(condition, of);

		assertThat(promotions.getContent()).hasSize(3);
	}

	@Test
	void stocksGroupByPromotion(){
		PromotionSearchCondition condition = new PromotionSearchCondition(false, "title1", LocalDateTime.of(2023, 10, 31, 0, 0));
		List<Tuple> tuples = repository.stocksGroupByPromotion(condition);
		for (Tuple tuple : tuples) {
			PromotionId promotionId = tuple.get(0, PromotionId.class);
			if (promotionId.equals(PromotionId.of("promotion1"))){
				assertThat(tuple.get(1, Long.class)).isEqualTo(2L);
			}
			if (promotionId.equals(PromotionId.of("promotion2"))){
				assertThat(tuple.get(1, Long.class)).isEqualTo(2L);
			}
			if (promotionId.equals(PromotionId.of("promotion3"))){
				assertThat(tuple.get(1, Long.class)).isEqualTo(1L);
			}
		}
	}
}