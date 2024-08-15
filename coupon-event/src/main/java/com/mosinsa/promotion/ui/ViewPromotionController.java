package com.mosinsa.promotion.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.GuestOrLogin;
import com.mosinsa.promotion.query.PromotionQueryService;
import com.mosinsa.promotion.query.dto.PromotionDetails;
import com.mosinsa.promotion.query.dto.PromotionSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ViewPromotionController {

	private final PromotionQueryService promotionQueryService;

	@GetMapping("/promotions")
	public ResponseEntity<Page<PromotionSummary>> promotions(Pageable pageable) {
		Page<PromotionSummary> promotions = promotionQueryService.findPromotions(pageable);
		return ResponseEntity.ok(promotions);
	}

	@GetMapping("/promotions/{promotionId}")
	public ResponseEntity<PromotionDetails> getDetails(@PathVariable("promotionId") String promotionId, @GuestOrLogin CustomerInfo customerInfo) {
		log.info("{}", promotionId);

		PromotionDetails promotionDetails = promotionQueryService.getPromotionDetails(promotionId, customerInfo.id());

		return ResponseEntity.ok(promotionDetails);
	}

}
