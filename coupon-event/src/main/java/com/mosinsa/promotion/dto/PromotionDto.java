package com.mosinsa.promotion.dto;

import com.mosinsa.coupon.domain.DiscountPolicy;
import com.mosinsa.promotion.domain.Promotion;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.domain.PromotionPeriod;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PromotionDto {

	private final String promotionId;

	private final String title;

	private final String context;

	private final int quantity;

	private final DiscountPolicy discountPolicy;

	private final PromotionPeriod period;

	@QueryProjection
	public PromotionDto(PromotionId promotionId, String title, String context, int quantity, DiscountPolicy discountPolicy, PromotionPeriod period) {
		this.promotionId = promotionId.getId();
		this.title = title;
		this.context = context;
		this.quantity = quantity;
		this.discountPolicy = discountPolicy;
		this.period = period;
	}

	public static PromotionDto convert(Promotion promotion) {
		return new PromotionDto(promotion.getPromotionId(),
				promotion.getTitle(),
				promotion.getContext(),
				promotion.getQuantity(),
				promotion.getDiscountPolicy(),
				promotion.getPeriod());
	}
}
