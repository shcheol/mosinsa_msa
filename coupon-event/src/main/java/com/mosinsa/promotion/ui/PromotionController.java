package com.mosinsa.promotion.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.promotion.command.application.PromotionService;
import com.mosinsa.promotion.command.application.dto.ParticipateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/promotions/{promotionId}")
    public ResponseEntity<ParticipateResponse> participate(@PathVariable("promotionId") String promotionId,
                                                           @RequestBody ParticipateRequest participateRequest,
                                                           @Login CustomerInfo customerInfo) {

        log.info("{}", participateRequest);
        ParticipateDto participateDto = new ParticipateDto(promotionId, customerInfo.id(), participateRequest.quests());
        log.info("{}", participateDto);
        promotionService.participatePromotion(participateDto);

        ParticipateResponse participateResponse = new ParticipateResponse("", 0L);
        return ResponseEntity.ok().body(participateResponse);
    }
}
