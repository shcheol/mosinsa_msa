package com.mosinsa.promotion.query.dto;

import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionPeriod;

import java.util.List;

public record PromotionDetails(String promotionId, String title, String context,
							   PromotionPeriod period, boolean participated, List<QuestDto> quests) {

	public static PromotionDetails of(Promotion promotion, boolean participated, List<QuestDto> quests) {
		return new PromotionDetails(promotion.getId().getId(),
				promotion.getTitle(),
				promotion.getContext(),
				promotion.getPeriod(),
				participated,
				quests);
	}
}
