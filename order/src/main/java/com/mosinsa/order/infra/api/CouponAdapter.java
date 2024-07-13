package com.mosinsa.order.infra.api;

import com.mosinsa.order.infra.api.feignclient.coupon.CouponClient;
import com.mosinsa.order.infra.api.feignclient.coupon.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CouponAdapter {

    private final CouponClient client;

    public ResponseResult<Void> useCoupon(Map<String, Collection<String>> headers, String couponId) {
        if (!StringUtils.hasText(couponId)) {
            return ResponseResult.empty();
        }

        return ResponseResult.execute(() -> client.useCoupon(headers, couponId));
    }

    public ResponseResult<CouponResponse> getCoupon(Map<String, Collection<String>> headers, String couponId) {
        if (!StringUtils.hasText(couponId)) {
            return ResponseResult.empty();
        }

        return ResponseResult.execute(() -> client.getCoupon(headers, couponId));
    }

}
