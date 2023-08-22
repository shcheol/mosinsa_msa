package com.mosinsa.couponissue.pub;

import com.mosinsa.couponissue.entity.CouponEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Async
    public void publishCouponEvent(CouponEvent event){
        applicationEventPublisher.publishEvent(event);
    }

}
