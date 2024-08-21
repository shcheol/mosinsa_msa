package com.mosinsa.coupon.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.coupon.ui.CouponServiceStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionParticipatedEventHandlerTest {

	@Test
	@DisplayName("프로모션 참여 이벤트")
	void promotionParticipatedEvent() throws JsonProcessingException {
		String message= """
				{
					"memberId": "customer1",
					"couponGroupSequence": 1
				}
				""";

		CouponServiceStub couponService = new CouponServiceStub();
		assertThat(couponService.getIssueCalledCount()).isZero();
		PromotionParticipatedEventHandler promotionParticipatedEventHandler = new PromotionParticipatedEventHandler(couponService, new ObjectMapper());
		promotionParticipatedEventHandler.promotionParticipatedEvent(message);
		assertThat(couponService.getIssueCalledCount()).isEqualTo(1);
	}
}