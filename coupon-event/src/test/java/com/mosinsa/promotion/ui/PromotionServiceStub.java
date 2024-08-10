package com.mosinsa.promotion.ui;

import com.mosinsa.promotion.command.application.PromotionService;
import com.mosinsa.promotion.command.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.command.domain.PromotionId;

import java.time.LocalDateTime;


public class PromotionServiceStub implements PromotionService {

    @Override
    public PromotionId registerPromotion(CreatePromotionRequest request) {
        return PromotionId.of("testId");
    }
}
