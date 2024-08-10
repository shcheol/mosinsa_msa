package com.mosinsa.promotion.ui;

import com.mosinsa.promotion.command.application.PromotionService;
import com.mosinsa.promotion.command.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.command.domain.PromotionId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/promotions")
    public ResponseEntity<PromotionId> register(@RequestBody CreatePromotionRequest request) {
        log.info("request {}", request);
        PromotionId promotionId = promotionService.registerPromotion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(promotionId);
    }
}
