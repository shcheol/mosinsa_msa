package com.shopping.mosinsa.scheduler;

import com.shopping.mosinsa.service.CouponQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponQueueScheduler{

	private final CouponQueueService service;

	@Scheduled(fixedDelay = 5000)
	public void dequeueCoupon() {
		try {

			log.info("Batch start: {}", LocalDateTime.now());
//			CouponEvent couponEvent = service.getCouponEvent();
//			String key = String.format("%s%s",couponEvent.getEventName(),couponEvent.getId());
			String key = "10주년이벤트21";
			service.dequeuePart(key,10);


		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
