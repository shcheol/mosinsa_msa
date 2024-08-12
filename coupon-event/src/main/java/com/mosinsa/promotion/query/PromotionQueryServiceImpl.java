package com.mosinsa.promotion.query;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import com.mosinsa.promotion.query.dto.PromotionDetails;
import com.mosinsa.promotion.query.dto.PromotionSummary;
import com.mosinsa.promotion.query.dto.PromotionSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionQueryServiceImpl implements PromotionQueryService {

	private final PromotionRepository repository;

	@Override
	@Transactional
	public PromotionDetails getPromotionDetails(String promotionId) {
		Promotion promotion = repository.findById(PromotionId.of(promotionId)).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND));
//		return PromotionSummary.of(promotion);
		return null;
	}

	@Override
	@Transactional
	public Page<PromotionSummary> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable) {
		return repository.findPromotionsByCondition(condition, pageable);
	}

}
