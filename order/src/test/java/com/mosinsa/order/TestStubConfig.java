package com.mosinsa.order;

import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.ProductAdapter;
import com.mosinsa.order.infra.stub.StubCouponAdapter;
import com.mosinsa.order.infra.stub.StubProductAdapter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestStubConfig {

    @Bean
    public CouponAdapter couponAdapter(){
        return new StubCouponAdapter();
    }

    @Bean
    @Primary
    public ProductAdapter productAdapter(){
        return new StubProductAdapter();
    }
}
