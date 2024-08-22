package com.mosinsa.promotion.command.application;

import com.mosinsa.coupon.command.domain.CouponCondition;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionId;
import com.mosinsa.promotion.command.domain.PromotionPeriod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PromotionServiceImplTest {

	@Autowired
	private PromotionServiceImpl promotionService;

	@Test
	void create() {

	}
}