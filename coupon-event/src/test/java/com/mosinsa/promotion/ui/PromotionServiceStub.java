package com.mosinsa.promotion.ui;

import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.application.PromotionService;
import com.mosinsa.promotion.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import com.mosinsa.promotion.domain.Promotion;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.domain.PromotionPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public class PromotionServiceStub implements PromotionService {

	LocalDateTime before = LocalDateTime.of(2023, 10, 28, 00, 00);
	LocalDateTime after = LocalDateTime.of(2024, 10, 28, 00, 00);
	@Override
	public PromotionId registerPromotion(CreatePromotionRequest request) {
		return PromotionId.of("testId");
	}

	@Override
	public PromotionDto getPromotionDetails(String promotionId) {

		return PromotionDto.of(Promotion.create(
				"title", "context", 3, DiscountPolicy.TEN_PERCENTAGE,
				new PromotionPeriod(before, after),
				CouponDetails.of(after, DiscountPolicy.TEN_PERCENTAGE))
		);
	}

	@Override
	public Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable) {
		PromotionDto dto1 = PromotionDto.of(Promotion.create(
				"title", "context", 3, DiscountPolicy.TEN_PERCENTAGE,
				new PromotionPeriod(before, after),
				CouponDetails.of(after, DiscountPolicy.TEN_PERCENTAGE))
		);
		PromotionDto dto2 = PromotionDto.of(Promotion.create(
				"title", "context", 3, DiscountPolicy.TEN_PERCENTAGE,
				new PromotionPeriod(before, after),
				CouponDetails.of(after, DiscountPolicy.TEN_PERCENTAGE))
		);

		PageImpl<PromotionDto> page = new PageImpl<>(List.of(dto1, dto2), PageRequest.of(0, 2), 2);
		return page;
	}
}
