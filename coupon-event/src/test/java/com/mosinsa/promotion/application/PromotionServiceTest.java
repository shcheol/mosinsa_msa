package com.mosinsa.promotion.application;

import com.mosinsa.coupon.domain.CouponDetails;
import com.mosinsa.coupon.domain.DiscountPolicy;
import com.mosinsa.promotion.domain.PromotionPeriod;
import com.mosinsa.promotion.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.application.dto.PromotionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PromotionServiceTest {

    @Autowired
    private PromotionService promotionService;


    @Test
    @Commit
    void create() {
        CreatePromotionRequest createPromotionRequest = new CreatePromotionRequest("title", "context", 500, DiscountPolicy.TEN_PERCENTAGE,
                new PromotionPeriod(LocalDateTime.now(), LocalDateTime.of(2024, 10, 28, 00, 00)),
				CouponDetails.of(LocalDateTime.of(2024, 10, 28, 00, 00), DiscountPolicy.TEN_PERCENTAGE));
        PromotionDto promotion = promotionService.create(createPromotionRequest);
        PromotionDto promotionDto = promotionService.findByPromotionId(promotion.getPromotionId());
        assertThat(promotion.getPromotionId()).isEqualTo(promotionDto.getPromotionId());
        assertThat(promotion.getTitle()).isEqualTo(promotionDto.getTitle());
    }
}