package com.shopping.mosinsa.sub;

import com.shopping.mosinsa.config.CouponBatchConfig;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.service.CouponQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponEventSubscriber {

	private final CouponQueueService service;
	private final CouponBatchConfig batchConfig;
	private final JobLauncher jobLauncher;
	@EventListener
	@Async
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void onCustomEvent(CouponEvent event) {

		try {

			String key = event.getEventName() + event.getId();
			log.info("Event {} start: {}", key, LocalDateTime.now());
//			service.dequeuePart(key,10);

			jobLauncher.run(
					batchConfig.couponJob(),
					new JobParametersBuilder()
							.addString("key",key)
							.toJobParameters()
			);
			log.info("Event {} end: {}", key, LocalDateTime.now());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
