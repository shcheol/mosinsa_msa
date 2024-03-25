package com.mosinsa.order.infra.feignclient.coupon;

import com.mosinsa.order.infra.feignclient.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponClient couponClient;

    public ResponseResult<CouponResponse> useCoupon(Map<String, Collection<String>> headers, String couponId) {
        return ResponseResult.execute(() -> couponClient.useCoupon(headers, couponId));
    }

    public ResponseResult<CouponResponse> cancelCoupon(Map<String, Collection<String>> headers, String couponId) {
        return ResponseResult.execute(() -> couponClient.cancelCoupon(headers, couponId));
    }
}
