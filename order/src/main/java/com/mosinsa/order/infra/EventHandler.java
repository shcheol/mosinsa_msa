package com.mosinsa.order.infra;

import com.mosinsa.order.infra.kafka.KafkaEvents;
import com.mosinsa.order.infra.kafka.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class EventHandler {

	@TransactionalEventListener(
			classes = OrderCreatedEvent.class,
			phase = TransactionPhase.AFTER_COMMIT
	)
	public void handle(OrderCreatedEvent event) {
		log.info("handle OrderCreatedEvent");
		KafkaEvents.raise(event.couponId(), event);
	}

}
