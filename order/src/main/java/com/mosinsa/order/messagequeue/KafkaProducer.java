package com.mosinsa.order.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, OrderDto orderDto, String idempotencyKey){
        ObjectMapper om = new ObjectMapper();
        String s="";
        try {
            s = om.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
		ProducerRecord<String, String> record = new ProducerRecord<>(topic, s);
		record.headers().add("IdempotencyKey", idempotencyKey.getBytes(StandardCharsets.UTF_8));
		kafkaTemplate.send(record);
        log.info("send {}", s);
    }

	public void send(String topic, OrderDto orderDto){
		ObjectMapper om = new ObjectMapper();
		String s="";
		try {
			s = om.writeValueAsString(orderDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		kafkaTemplate.send(topic, s);
		log.info("send {}", s);
	}
}
