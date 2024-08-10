package com.mosinsa.coupon.command.application;

import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.infra.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CouponServiceImplTest {

    @Autowired
    CouponServiceImpl service;

    @Autowired
    CouponRepository repository;

    @Test
    @Commit
    @Transactional
    @DisplayName("쿠폰발급")
    void issue() {
        CouponIssuedEvent event = new CouponIssuedEvent("1", 3000L, LocalDate.now().plusDays(30), DiscountPolicy.TEN_PERCENTAGE);
        CouponId couponId = service.issue(event);

        Coupon coupon = repository.findById(couponId).get();

        assertThat(coupon.getMemberId()).isEqualTo("1");
        assertThat(coupon.getIssuedDate()).isNotNull();
        assertThat(coupon.getState()).isEqualTo(CouponState.ISSUED);
    }

}