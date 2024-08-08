package com.mosinsa.promotion.application.dto;

import com.mosinsa.coupon.command.domain.DiscountPolicy;
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

	private final int stock;

	private final DiscountPolicy discountPolicy;

	private final PromotionPeriod period;

	@QueryProjection
	public PromotionDto(PromotionId promotionId, String title, String context, int stock, DiscountPolicy discountPolicy, PromotionPeriod period) {
		this.promotionId = promotionId.getId();
		this.title = title;
		this.context = context;
		this.stock = stock;
		this.discountPolicy = discountPolicy;
		this.period = period;
	}

	public static PromotionDto of(Promotion promotion) {
		return new PromotionDto(promotion.getId(),
				promotion.getTitle(),
				promotion.getContext(),
				promotion.getStock(),
				promotion.getDiscountPolicy(),
				promotion.getPeriod());
	}
}
