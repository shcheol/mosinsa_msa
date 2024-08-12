package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.query.dto.PromotionSummary;
import com.mosinsa.promotion.query.dto.PromotionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPromotionRepository {

	Page<PromotionSummary> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable);
}
