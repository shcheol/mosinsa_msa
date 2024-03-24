package com.mosinsa.order.infra.feignclient.coupon;

import com.mosinsa.order.infra.feignclient.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponClient couponClient;

    public SimpleCouponResponse couponCheck(Map<String, Collection<String>> headers, String couponId) {

        if (!StringUtils.hasText(couponId)) {
            log.debug("order without coupon");
            return SimpleCouponResponse.empty();
        }

        ResponseResult<CouponResponse> execute = ResponseResult.execute(() -> couponClient.getCoupon(headers, couponId));
        CouponResponse couponResponse = execute.orElse(null);
        return SimpleCouponResponse.of(couponResponse);

    }

}
