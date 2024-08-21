package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class PromotionConditionTest extends InMemoryJpaTest {


	@Test
	void save(){
		PromotionCondition of = PromotionCondition.of(PromotionConditions.NEW_MEMBER);

	}

}