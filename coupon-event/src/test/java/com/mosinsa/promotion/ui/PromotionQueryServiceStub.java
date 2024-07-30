package com.mosinsa.promotion.ui;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionPeriod;
import com.mosinsa.promotion.query.PromotionQueryService;
import com.mosinsa.promotion.query.dto.PromotionDetails;
import com.mosinsa.promotion.query.dto.PromotionSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionQueryServiceStub implements PromotionQueryService {


	LocalDateTime before = LocalDateTime.of(2023, 10, 28, 00, 00);
	LocalDateTime after = LocalDateTime.of(2024, 10, 28, 00, 00);

	@Override
	public PromotionDetails getPromotionDetails(String promotionId, String memberId) {

		return PromotionDetails.of(
				Promotion.create("title", "context", DateUnit.ONCE,  new PromotionPeriod(before, after)),
				false,
				List.of()
		);
	}

	@Override
	public Page<PromotionSummary> findPromotions(Pageable pageable) {
		PromotionSummary dto1 = PromotionSummary.of(Promotion.create(
				"title", "context",DateUnit.ONCE,  new PromotionPeriod(before, after))
		);
		PromotionSummary dto2 = PromotionSummary.of(Promotion.create(
				"title", "context",DateUnit.ONCE,  new PromotionPeriod(before, after))
		);

		return new PageImpl<>(List.of(dto1, dto2), PageRequest.of(0, 2), 2);
	}
}
