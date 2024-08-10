package com.mosinsa.promotion.command.application;

import com.mosinsa.promotion.command.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.command.domain.PromotionId;

public interface PromotionService {
    PromotionId registerPromotion(CreatePromotionRequest request);
}
