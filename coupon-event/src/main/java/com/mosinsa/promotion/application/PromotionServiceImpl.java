package com.mosinsa.promotion.application;

import com.mosinsa.common.event.Events;
import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.promotion.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import com.mosinsa.promotion.domain.Promotion;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

	private final PromotionRepository repository;

	@Override
	@Transactional
	public PromotionId registerPromotion(CreatePromotionRequest request) {
		CouponDetails couponDetails = CouponDetails.of(request.details().getDuringDate(), request.discountPolicy());
		Promotion promotion = Promotion.create(request.title(),
				request.context(),
				request.quantity(),
				request.discountPolicy(),
				request.period());
		Events.raise(new PromotionCreatedEvent(promotion.getId(), promotion.getStock(), couponDetails));
		return repository.save(promotion).getId();
	}

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
