package com.mosinsa.promotion.infra;

import com.mosinsa.coupon.application.CouponService;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionCreatedEventHandler {

    private final CouponService couponService;

    @Async
//    @TransactionalEventListener(
//            classes = PromotionCreatedEvent.class,
//            phase = TransactionPhase.AFTER_COMMIT
//    )
    @EventListener(PromotionCreatedEvent.class)
    public void handle(PromotionCreatedEvent event) {
        log.info("handle PromotionCreatedEvent");
        couponService.createAllByBatchInsert(event);
    }
}
