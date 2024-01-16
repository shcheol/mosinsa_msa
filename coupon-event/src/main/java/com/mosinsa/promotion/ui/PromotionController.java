package com.mosinsa.promotion.ui;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.promotion.application.PromotionService;
import com.mosinsa.promotion.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PromotionController {

	private final PromotionService promotionService;

	private final CouponService couponService;

	@GetMapping("/promotions")
	public ResponseEntity<Page<PromotionDto>> promotions(PromotionSearchCondition condition, Pageable pageable, Model model) {
		Page<PromotionDto> promotions = promotionService.findByPromotions(condition, pageable);
		model.addAttribute("promotions", promotions);
		return ResponseEntity.ok(promotions);
	}

	@GetMapping("/promotions/{promotionId}")
	public ResponseEntity<PromotionDetails> detail(@PathVariable("promotionId") String promotionId, Model model) {
		log.info("{}", promotionId);

		PromotionDto promotionDto = promotionService.findByPromotionId(promotionId);
		long count = couponService.count(promotionId);

		PromotionDetails promotionDetails = new PromotionDetails(promotionDto, count);
		return ResponseEntity.ok(promotionDetails);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/promotions")
	public PromotionDto create(@RequestBody CreatePromotionRequest request) {
		log.info("request {}", request);
		return promotionService.create(request);
	}

	@PatchMapping(value = "/promotions", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JoinResult> joinPromotion(@RequestBody JoinPromotionRequest request) {
		String promotionId = request.promotionId();
		String memberId = request.memberId();
		log.info("promotionId: {}, memberId: {}", promotionId, memberId);
		if (!StringUtils.hasText(memberId)) {
			return ResponseEntity.badRequest().body(new JoinResult("login first"));
		}

		promotionService.joinPromotion(memberId, promotionId);

		return ResponseEntity.ok(new JoinResult("request..."));
	}

	@ExceptionHandler(CouponException.class)
	public String handleNoPromotion() {
		return "promotion/noPromotion";
	}
}
