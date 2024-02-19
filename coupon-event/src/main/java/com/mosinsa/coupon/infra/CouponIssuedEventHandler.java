package com.mosinsa.coupon.infra;

import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.coupon.domain.CouponIssuedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponIssuedEventHandler {

    private final CouponService couponService;

    @Async
    @EventListener(CouponIssuedEvent.class)
    public void handle(CouponIssuedEvent event) {
        log.info("handle PromotionCreatedEvent");
        couponService.issue(event);
    }
}
