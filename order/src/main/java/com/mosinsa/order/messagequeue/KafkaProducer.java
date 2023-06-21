package com.mosinsa.order.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.dto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, OrderProductDto orderProductDto){
        ObjectMapper om = new ObjectMapper();
        String s="";
        try {
            s = om.writeValueAsString(orderProductDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send(topic, s);
        log.info("send {}", s);
    }
}
