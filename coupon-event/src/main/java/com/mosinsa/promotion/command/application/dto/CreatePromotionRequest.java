package com.mosinsa.promotion.command.application.dto;

import com.mosinsa.coupon.command.domain.CouponCondition;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.command.domain.PromotionPeriod;

public record CreatePromotionRequest(String title, String context, int quantity, DiscountPolicy discountPolicy,
                                     PromotionPeriod period, CouponCondition details) {
}
