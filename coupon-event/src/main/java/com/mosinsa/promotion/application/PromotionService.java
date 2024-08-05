package com.mosinsa.promotion.application;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.promotion.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import com.mosinsa.promotion.domain.Promotion;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionService {

	private final PromotionRepository repository;

	@Transactional
	public PromotionDto create(CreatePromotionRequest request) {
		return PromotionDto.convert(
				repository.save(
						Promotion.create(request.title(),
								request.context(),
								request.quantity(),
								request.discountPolicy(),
								request.period(),
								CouponDetails.of(request.details().getDuringDate(), request.discountPolicy()))));
	}

	@Transactional
	public PromotionDto findByPromotionId(String promotionId) {
		Promotion promotion = repository.findById(PromotionId.of(promotionId)).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND));
		return PromotionDto.convert(promotion);
	}

	@Transactional
	public Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable) {
		return repository.findPromotionsByCondition(condition, pageable);
	}

}
