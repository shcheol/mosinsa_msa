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
	public ResponseEntity<PromotionDetails> detail(@PathVariable("promotionId") String promotionId) {
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

	@PostMapping(value = "/promotions/{promotionId}/join", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JoinResult> joinPromotion(@PathVariable("promotionId") String promotionId, @RequestBody JoinPromotionRequest request) {

		validateLogin(request.memberId());

		promotionService.joinPromotion(request.memberId(), promotionId);

		return ResponseEntity.ok(new JoinResult("request..."));
	}

	private void validateLogin(String memberId) {
		if(!StringUtils.hasText(memberId)){
			log.warn("login before join promotion request: {}", memberId);
			throw new NotLoginRequestException();
		}
	}

	@ExceptionHandler(NotLoginRequestException.class)
	public ResponseEntity<JoinResult> notLoginRequest() {
		return ResponseEntity.badRequest().body(new JoinResult("login first"));
	}
}
