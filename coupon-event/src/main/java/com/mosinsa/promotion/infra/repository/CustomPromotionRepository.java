package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.dto.PromotionDto;
import com.mosinsa.promotion.dto.PromotionSearchCondition;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomPromotionRepository {

	Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable);

	List<Tuple> stocksGroupByPromotion(PromotionSearchCondition condition);
}
