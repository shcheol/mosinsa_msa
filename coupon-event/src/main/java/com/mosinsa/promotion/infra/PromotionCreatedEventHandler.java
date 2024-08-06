package com.mosinsa.promotion.infra;

import com.mosinsa.coupon.command.application.CouponService;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionCreatedEventHandler {

    private final CouponService couponService;

    @Async
    @TransactionalEventListener(
            classes = PromotionCreatedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(PromotionCreatedEvent event) {
        log.info("handle PromotionCreatedEvent");
        couponService.createAllByBatchInsert(event);
    }
}
