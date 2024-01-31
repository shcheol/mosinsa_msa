package com.mosinsa.customer.infra;

import com.mosinsa.customer.infra.kafka.CustomerCreatedEvent;
import com.mosinsa.customer.infra.kafka.KafkaEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class EventHandler {

	@TransactionalEventListener(
			classes = CustomerCreatedEvent.class,
			phase = TransactionPhase.AFTER_COMMIT
	)
	public void handle(CustomerCreatedEvent event) {
		log.info("handle customer created event");
		KafkaEvents.raise(event.customerId(), event);
	}

}
