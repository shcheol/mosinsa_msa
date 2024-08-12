package com.mosinsa.promotion.query;

import com.mosinsa.promotion.query.dto.PromotionDetails;
import com.mosinsa.promotion.query.dto.PromotionSummary;
import com.mosinsa.promotion.query.dto.PromotionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionQueryService {
    PromotionDetails getPromotionDetails(String promotionId);

    Page<PromotionSummary> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable);
}
