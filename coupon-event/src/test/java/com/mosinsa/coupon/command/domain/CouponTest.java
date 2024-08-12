package com.mosinsa.coupon.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.coupon.infra.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CouponTest {

	@Autowired
	CouponRepository repository;

	@Test
	void useAndCancel() {
		Coupon coupon = Coupon.issue("memberId",
				CouponCondition.of(3000L, LocalDate.now(), DiscountPolicy.TEN_PERCENTAGE));
		assertThat(coupon.getState()).isEqualTo(CouponState.ISSUED);

		coupon.use();
		assertThat(coupon.getState()).isEqualTo(CouponState.USED);

		assertThrows(InvalidCouponStateException.class, () -> coupon.use());

		coupon.useCancel();
		assertThat(coupon.getState()).isEqualTo(CouponState.ISSUED);
		assertThrows(InvalidCouponStateException.class, () -> coupon.useCancel());
	}

	@Test
	void equalsAndHashcode(){
		Coupon coupon = Coupon.issue("memberId",
				CouponCondition.of(3000L, LocalDate.now(), DiscountPolicy.TEN_PERCENTAGE));
		Coupon save = repository.save(coupon);
		Coupon coupon1 = repository.findById(save.getId()).get();
		Coupon protectedConstructor = new Coupon();
		Coupon coupon2 = Coupon.issue("memberId",
				CouponCondition.of(3000L, LocalDate.now(), DiscountPolicy.TEN_PERCENTAGE));
		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(coupon, coupon1, protectedConstructor, coupon2);
		assertThat(b).isTrue();
	}

}