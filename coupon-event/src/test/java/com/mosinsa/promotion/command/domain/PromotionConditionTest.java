package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:db/test-init.sql")
class PromotionConditionTest extends InMemoryJpaTest {

	@Autowired
	PromotionRepository repository;

	@Test
	@Transactional
	void save(){
		PromotionCondition condition1 = repository.findDetailsById(PromotionId.of("promotion1")).get()
				.getPromotionCondition();
		PromotionCondition condition2 = repository.findDetailsById(PromotionId.of("promotion1")).get()
				.getPromotionCondition();
		PromotionCondition protectedConstructor = new PromotionCondition();
		PromotionCondition other = repository.findById(PromotionId.of("promotion4")).get().getPromotionCondition();

		System.out.println(condition1.getConditions());

//		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(condition1, condition2, protectedConstructor, other);
//		assertThat(b).isTrue();

	}

}