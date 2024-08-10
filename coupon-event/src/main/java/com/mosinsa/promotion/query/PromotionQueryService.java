package com.mosinsa.promotion.query;

import com.mosinsa.promotion.query.dto.PromotionDto;
import com.mosinsa.promotion.query.dto.PromotionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionQueryService {
    PromotionDto getPromotionDetails(String promotionId);

    Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable);
}
