package com.mosinsa.coupon.command.application;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.command.domain.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CouponServiceImplTest {

    @Autowired
    CouponServiceImpl service;

    @Autowired
    CouponRepository repository;

    @Test
    @DisplayName("쿠폰발급")
    void issue() {
        CouponId couponId = service.issue("memberId", 1L);

        Coupon coupon = repository.findById(couponId).get();

        assertThat(coupon.getMemberId()).isEqualTo("memberId");
        assertThat(coupon.getIssuedDate()).isNotNull();
        assertThat(coupon.getState()).isEqualTo(CouponState.ISSUED);
    }

	@Test
	@DisplayName("쿠폰사용")
	void use() {
		CouponId id = CouponId.of("coupon1");
		Coupon couponBefore = repository.findById(id).get();
		assertThat(couponBefore.getState()).isEqualTo(CouponState.ISSUED);
		service.useCoupon(id.getId());
		Coupon couponAfter = repository.findById(id).get();
		assertThat(couponAfter.getState()).isEqualTo(CouponState.USED);

		assertThrows(CouponException.class, ()-> service.useCoupon("xxx"));
	}


	@Test
	@DisplayName("쿠폰사용취소")
	void cancel() {
		CouponId id = CouponId.of("coupon1");
		service.useCoupon(id.getId());
		Coupon couponBefore = repository.findById(id).get();
		assertThat(couponBefore.getState()).isEqualTo(CouponState.USED);

		service.cancelCoupon(id.getId());
		Coupon couponAfter = repository.findById(id).get();
		assertThat(couponAfter.getState()).isEqualTo(CouponState.ISSUED);

		assertThrows(CouponException.class, ()-> service.cancelCoupon("xxx"));
	}
}