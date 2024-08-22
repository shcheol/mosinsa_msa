package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import org.junit.jupiter.api.Test;

class PromotionConditionTest extends InMemoryJpaTest {


	@Test
	void save(){
		PromotionCondition of = PromotionCondition.of(PromotionConditions.NEW_OR_OLD_MEMBER);

	}

}