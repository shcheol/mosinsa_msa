package com.mosinsa.coupon.command.application;

import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.infra.jpa.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

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

}