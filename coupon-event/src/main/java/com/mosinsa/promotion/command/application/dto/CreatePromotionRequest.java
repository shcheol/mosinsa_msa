package com.mosinsa.promotion.command.application.dto;

import com.mosinsa.coupon.command.domain.CouponCondition;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionPeriod;

public record CreatePromotionRequest(String title, String context, int quantity, DiscountPolicy discountPolicy,
									 DateUnit dateUnit,
                                     PromotionPeriod period, CouponCondition details) {
}
