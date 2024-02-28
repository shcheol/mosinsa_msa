package com.mosinsa.coupon.infra;

import com.mosinsa.common.event.Events;
import com.mosinsa.coupon.domain.CouponIssuedEvent;
import com.mosinsa.coupon.application.dto.CouponDto;
import com.mosinsa.coupon.infra.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CouponIssuedEventHandlerTest {

	@Autowired
	CouponRepository repository;

	@Test
	void eventHandler() throws InterruptedException {
		String promotionId = "promotion1";
		String memberId = "1";

		Optional<CouponDto> before = repository.findMyCoupons(memberId).stream().filter(c -> c.getPromotionId().equals(promotionId)).findFirst();
		assertThat(before).isEmpty();

		Events.raise(new CouponIssuedEvent(memberId, promotionId));

		Thread.sleep(500);

		Optional<CouponDto> after = repository.findMyCoupons(memberId).stream().filter(c -> c.getPromotionId().equals(promotionId)).findFirst();
		assertThat(after).isNotEmpty();
	}

}