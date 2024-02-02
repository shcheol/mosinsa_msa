package com.mosinsa.order.common.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
public class Events {

	private static ApplicationEventPublisher publisher;

	static void setPublisher(ApplicationEventPublisher publisher) {
		Events.publisher = publisher;
	}

	public static void raise(Object event) {
		if (publisher == null) {
			log.info("publisher is null");
		}
		log.info("publish event");
		publisher.publishEvent(event);

	}

	private Events() {
	}

}
