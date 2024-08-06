package com.mosinsa.promotion.ui;

import com.mosinsa.coupon.query.application.CouponQueryService;
import com.mosinsa.coupon.query.application.dto.CouponDto;
import com.mosinsa.promotion.application.PromotionService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

@TestConfiguration
public class PromotionPresentationObjectFactory {

	@Bean
	public PromotionService promotionService() {
		return new PromotionServiceStub();
	}

	@Bean
	public CouponQueryService couponQueryService(){
		return new CouponQueryService() {
			@Override
			public CouponDto getCouponDetails(String couponId) {
				return null;
			}

			@Override
			public long count(String promotionId) {
				return 0;
			}

			@Override
			public List<CouponDto> getMyCoupons(String memberId) {
				return null;
			}
		};
	}
}
