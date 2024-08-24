package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:db/test-init.sql")
class PromotionConditionTest extends InMemoryJpaTest {

    @Autowired
    PromotionRepository repository;

    @Test
    void equalsAndHashcode() {
        PromotionCondition condition1 = repository.findDetailsById(PromotionId.of("promotion1")).get()
                .getPromotionCondition();
        PromotionCondition condition2 = repository.findDetailsById(PromotionId.of("promotion1")).get()
                .getPromotionCondition();
        PromotionCondition protectedConstructor = new PromotionCondition();
        PromotionCondition other = repository.findById(PromotionId.of("promotion4")).get().getPromotionCondition();

        boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(condition1, condition2, protectedConstructor, other);
        assertThat(b).isTrue();

    }

	@Test
	void field() {
		PromotionCondition condition1 = repository.findDetailsById(PromotionId.of("promotion1")).get()
				.getPromotionCondition();

		Assertions.assertThat(condition1.getConditions()).isEqualTo(PromotionConditions.NEW_OR_OLD_MEMBER);
		Assertions.assertThat(condition1.getPromotionConditionOptions()).hasSize(2);

	}

}