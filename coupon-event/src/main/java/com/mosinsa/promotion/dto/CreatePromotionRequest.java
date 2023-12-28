package com.mosinsa.promotion.dto;

import com.mosinsa.coupon.domain.CouponDetails;
import com.mosinsa.coupon.domain.DiscountPolicy;
import com.mosinsa.promotion.domain.PromotionPeriod;

public record CreatePromotionRequest(String title, String context, int quantity, DiscountPolicy discountPolicy,
									 PromotionPeriod period, CouponDetails details) {
}
