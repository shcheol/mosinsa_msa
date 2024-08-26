package com.mosinsa.order.infra.api.feignclient.coupon;

import com.mosinsa.order.infra.api.ResponseResult;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.charset.Charset;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CouponFeignAdapterTest {

    @MockBean
    CouponClient client;

    @Autowired
    CouponFeignAdapter adapter;

    @Test
    void useCouponEmpty() {
        assertThat(adapter.useCoupon("")).isEqualTo(ResponseResult.empty());
        assertThat(adapter.useCoupon(null)).isEqualTo(ResponseResult.empty());
    }

    @Test
    void getCoupon() {
        when(client.getCoupon(any(), any()))
                .thenReturn(new CouponResponse("couponId",null,null,null,null,null,false));

        ResponseResult<CouponResponse> response = adapter.getCoupon("couponId");
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.get().couponId()).isEqualTo("couponId");
    }

    @Test
    void useCoupon() {
        doNothing().when(client).useCoupon(any(),any());

        ResponseResult<Void> response = adapter.useCoupon("couponId");
        Assertions.assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void getCouponEmpty() {
        assertThat(adapter.getCoupon("")).isEqualTo(ResponseResult.empty());
        assertThat(adapter.getCoupon(null)).isEqualTo(ResponseResult.empty());
    }
}