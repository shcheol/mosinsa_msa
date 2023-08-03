package com.mosinsa.order.service.kafka;

import com.mosinsa.order.db.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.mosinsa.order.service.converter.JsonConverter.writeOrderDtoAsString;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, OrderDto orderDto){
		kafkaTemplate.send(topic, writeOrderDtoAsString(orderDto));
	}
}
