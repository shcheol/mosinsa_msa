package com.mosinsa.promotion.query;

import com.mosinsa.promotion.query.dto.PromotionSummary;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class PromotionQueryServiceImplTest {

    @Autowired
    PromotionQueryServiceImpl service;

    @Test
    void getPromotionDetails() {
        service.getPromotionDetails("promotion1","");
    }

    @Test
    void findPromotionsByCondition() {
        Page<PromotionSummary> promotions = service.findPromotions(PageRequest.of(0, 4));

        Assertions.assertThat(promotions.getContent()).hasSize(4);
    }
}