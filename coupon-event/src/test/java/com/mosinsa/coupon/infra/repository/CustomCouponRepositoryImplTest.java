package com.mosinsa.coupon.infra.repository;

import com.mosinsa.coupon.domain.CouponId;
import com.mosinsa.coupon.dto.CouponSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomCouponRepositoryImplTest {

    @Autowired
    CouponRepository repository;

    @Test
    void conditionNullTest(){
        List<CouponId> couponsInPromotion = repository.findCouponsInPromotion(new CouponSearchCondition(null, null));

        assertThat(couponsInPromotion).hasSize(15);

    }

}