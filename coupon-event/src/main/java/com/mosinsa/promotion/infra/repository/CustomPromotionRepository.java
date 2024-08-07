package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPromotionRepository {

	Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable);
}
