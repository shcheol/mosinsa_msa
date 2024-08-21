package com.mosinsa.promotion.command.application;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.command.application.dto.ParticipateDto;
import com.mosinsa.promotion.query.dto.QuestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Sql("classpath:db/test-init.sql")
class PromotionServiceImplTest {

    @Autowired
    private PromotionServiceImpl promotionService;

    @Test
    void participate() {
        ParticipateDto participateDto = new ParticipateDto("promotion1", UUID.randomUUID().toString(), List.of(new QuestDto(1L)));
        promotionService.participatePromotion(participateDto);
        assertThrows(CouponException.class, () -> promotionService.participatePromotion(participateDto));
    }

    @Test
    void participateEx() {
        ParticipateDto participateDto = new ParticipateDto("promotion1xxx", "memberId1", List.of());
        assertThrows(CouponException.class,
                () -> promotionService.participatePromotion(participateDto));
    }
}