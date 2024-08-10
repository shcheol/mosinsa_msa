package com.mosinsa.promotion.query;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import com.mosinsa.promotion.query.dto.PromotionDto;
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
	public PromotionDto getPromotionDetails(String promotionId) {
		Promotion promotion = repository.findById(PromotionId.of(promotionId)).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND));
		return PromotionDto.of(promotion);
	}

	@Override
	@Transactional
	public Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable) {
		return repository.findPromotionsByCondition(condition, pageable);
	}

}
