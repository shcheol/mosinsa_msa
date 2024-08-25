package com.mosinsa.order.infra.api.feignclient.coupon;

import com.mosinsa.order.infra.api.CouponAdapter;
import com.mosinsa.order.infra.api.RequestHeaderExtractor;
import com.mosinsa.order.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CouponFeignAdapter implements CouponAdapter {

    private final CouponClient client;

    @Override
    public ResponseResult<Void> useCoupon(String couponId) {
        if (!StringUtils.hasText(couponId)) {
            return ResponseResult.empty();
        }

        Map<String, Collection<String>> headers = RequestHeaderExtractor.extract();
        return ResponseResult.execute(() -> client.useCoupon(headers, couponId));
    }

    @Override
    public ResponseResult<CouponResponse> getCoupon(String couponId) {
        if (!StringUtils.hasText(couponId)) {
            return ResponseResult.empty();
        }
        Map<String, Collection<String>> headers = RequestHeaderExtractor.extract();
        return ResponseResult.execute(() -> client.getCoupon(headers, couponId));
    }

}
