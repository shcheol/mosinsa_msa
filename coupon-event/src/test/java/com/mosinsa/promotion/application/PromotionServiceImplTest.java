package com.mosinsa.promotion.application;

import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.application.dto.PromotionDto;
import com.mosinsa.promotion.domain.PromotionId;
import com.mosinsa.promotion.domain.PromotionPeriod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PromotionServiceImplTest {

    @Autowired
    private PromotionServiceImpl promotionService;


    @Test
    void create() {
        CreatePromotionRequest createPromotionRequest = new CreatePromotionRequest("title", "context", 500, DiscountPolicy.TEN_PERCENTAGE,
                new PromotionPeriod(LocalDateTime.now(), LocalDateTime.of(2024, 10, 28, 00, 00)),
				CouponDetails.of(LocalDateTime.of(2024, 10, 28, 00, 00), DiscountPolicy.TEN_PERCENTAGE));

		PromotionId promotionId = promotionService.registerPromotion(createPromotionRequest);
		PromotionDto promotionDto = promotionService.getPromotionDetails(promotionId.getId());

        assertThat(promotionId.getId()).isEqualTo(promotionDto.getPromotionId());
        assertThat(promotionDto.getTitle()).isEqualTo("title");
    }
}