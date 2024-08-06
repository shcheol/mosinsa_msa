package com.mosinsa.promotion.ui;

import com.mosinsa.coupon.query.application.CouponQueryService;
import com.mosinsa.promotion.application.PromotionService;
import com.mosinsa.promotion.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.application.dto.PromotionDetails;
import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.application.dto.PromotionSearchCondition;
import com.mosinsa.promotion.domain.PromotionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PromotionController {

	private final PromotionService promotionService;

	private final CouponQueryService couponQueryService;

	@GetMapping("/promotions")
	public ResponseEntity<Page<PromotionDto>> promotions(PromotionSearchCondition condition, Pageable pageable) {
		Page<PromotionDto> promotions = promotionService.findPromotionsByCondition(condition, pageable);
		return ResponseEntity.ok(promotions);
	}

	@GetMapping("/promotions/{promotionId}")
	public ResponseEntity<PromotionDetails> getDetails(@PathVariable("promotionId") String promotionId) {
		log.info("{}", promotionId);

		PromotionDto promotionDto = promotionService.getPromotionDetails(promotionId);
		long count = couponQueryService.count(promotionId);

		PromotionDetails promotionDetails = new PromotionDetails(promotionDto, count);
		return ResponseEntity.ok(promotionDetails);
	}

	@PostMapping("/promotions")
	public ResponseEntity<PromotionId> register(@RequestBody CreatePromotionRequest request) {
		log.info("request {}", request);
		PromotionId promotionId = promotionService.registerPromotion(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(promotionId);
	}
}
