package com.mosinsa.promotion.query.dto;

import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionPeriod;

public record PromotionSummary(String promotionId, String title, String context, PromotionPeriod period,
                               boolean proceeding) {
    public static PromotionSummary of(Promotion promotion) {
        return new PromotionSummary(promotion.getId().getId(),
                promotion.getTitle(),
                promotion.getContext(),
                promotion.getPeriod(),
                promotion.isProceeding());
    }
}
