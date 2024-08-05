package com.mosinsa.coupon.ui;

import com.mosinsa.coupon.command.application.CouponService;
import com.mosinsa.coupon.query.application.CouponQueryService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CouponPresentationObjectFactory {

    @Bean
    public CouponQueryService couponQueryService(){
        return new CouponQueryServiceStub();
    }

    @Bean
    public CouponService couponService(){
        return new CouponServiceStub();
    }
}
