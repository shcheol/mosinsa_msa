package com.mosinsa.coupon.query.application;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.coupon.query.application.dto.CouponDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CouponQueryServiceImplTest {

    @Autowired
    CouponQueryServiceImpl service;

    @Test
    void myCoupons() {
        String memberId1 = "memberId1";
        List<CouponDetails> couponDtos1 = service.getMyCoupons(memberId1);
        assertThat(couponDtos1).hasSize(1);

        String memberId2 = "memberId2";
        List<CouponDetails> couponDtos2 = service.getMyCoupons(memberId2);
        assertThat(couponDtos2).hasSize(3);

        String memberId2xxx = "memberId2xxx";
        List<CouponDetails> couponDtos3 = service.getMyCoupons(memberId2xxx);
        assertThat(couponDtos3).isEmpty();
    }

    @Test
    void findById() {

        CouponId id = CouponId.of("coupon1");
        CouponDetails findCoupon = service.getCouponDetails(id.getId());
        assertThat(findCoupon.couponId()).isEqualTo(id.getId());

        String noId = CouponId.of("couponId1xxxxx").getId();
        assertThrows(CouponException.class, () -> service.getCouponDetails(noId));
    }

}