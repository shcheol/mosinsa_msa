package com.mosinsa.promotion.application.dto;

import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.domain.PromotionPeriod;

public record CreatePromotionRequest(String title, String context, int quantity, DiscountPolicy discountPolicy,
									 PromotionPeriod period, CouponDetails details) {
}
