package com.mosinsa.coupon.query.application;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.query.application.CouponQueryService;
import com.mosinsa.coupon.query.application.dto.CouponDto;
import com.mosinsa.coupon.command.domain.CouponId;
import com.mosinsa.promotion.domain.PromotionId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CouponQueryServiceImplTest {

    @Autowired
    CouponQueryService service;

    @Test
    void count() {
        PromotionId promotionTest2 = PromotionId.of("promotion2");
        long count1 = service.count(promotionTest2.getId());
        assertThat(count1).isEqualTo(2);

        PromotionId promotionTest3 = PromotionId.of("promotion3");
        long count2 = service.count(promotionTest3.getId());
        assertThat(count2).isEqualTo(1);
    }

    @Test
    void myCoupons() {
        String memberId1 = "1";
        List<CouponDto> couponDtos1 = service.getMyCoupons(memberId1);
        assertThat(couponDtos1).hasSize(1);

        String memberId2 = "2";
        List<CouponDto> couponDtos2 = service.getMyCoupons(memberId2);
        assertThat(couponDtos2).isEmpty();
    }

    @Test
    void findById() {

        CouponId id = CouponId.of("coupon6");
        CouponDto findCoupon = service.getCouponDetails(id.getId());
        assertThat(findCoupon.getCouponId()).isEqualTo(id.getId());

        String noId = CouponId.of("couponId1xxxxx").getId();
        assertThrows(CouponException.class, () -> service.getCouponDetails(noId));
    }

}