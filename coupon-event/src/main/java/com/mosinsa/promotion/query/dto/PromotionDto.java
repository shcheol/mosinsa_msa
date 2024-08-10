package com.mosinsa.promotion.query.dto;

import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import com.mosinsa.promotion.command.domain.PromotionPeriod;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PromotionDto {

	private final String promotionId;

	private final String title;

	private final String context;

	private final PromotionPeriod period;

	@QueryProjection
	public PromotionDto(PromotionId promotionId, String title, String context,PromotionPeriod period) {
		this.promotionId = promotionId.getId();
		this.title = title;
		this.context = context;
		this.period = period;
	}

	public static PromotionDto of(Promotion promotion) {
		return new PromotionDto(promotion.getId(),
				promotion.getTitle(),
				promotion.getContext(),
				promotion.getPeriod());
	}
}
