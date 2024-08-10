package com.mosinsa.promotion.ui;

import com.mosinsa.promotion.command.application.PromotionService;
import com.mosinsa.promotion.query.PromotionQueryService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class PromotionPresentationObjectFactory {

	@Bean
	public PromotionService promotionService() {
		return new PromotionServiceStub();
	}

	@Bean
	public PromotionQueryService promotionQueryService() {
		return new PromotionQueryServiceStub();
	}
}
