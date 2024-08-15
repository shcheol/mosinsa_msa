package com.mosinsa.promotion.query;

import com.mosinsa.promotion.query.dto.PromotionDetails;
import com.mosinsa.promotion.query.dto.PromotionSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionQueryService {
    PromotionDetails getPromotionDetails(String promotionId, String memberId);

    Page<PromotionSummary> findPromotions(Pageable pageable);
}
