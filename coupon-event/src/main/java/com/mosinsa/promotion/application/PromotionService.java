package com.mosinsa.promotion.application;

import com.mosinsa.promotion.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import com.mosinsa.promotion.domain.PromotionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionService {
	PromotionId registerPromotion(CreatePromotionRequest request);

	PromotionDto getPromotionDetails(String promotionId);

	Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable);
}
