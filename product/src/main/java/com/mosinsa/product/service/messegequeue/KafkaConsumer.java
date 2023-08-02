package com.mosinsa.product.service.messegequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.db.dto.OrderDto;
import com.mosinsa.product.db.repository.ProductRepository;
import com.mosinsa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductRepository repository;

//	private final IdempotencyRepository repository;

    private final ProductService service;
    private final ObjectMapper om;


    @KafkaListener(topics = "mosinsa-product-order")
    public void orderProduct(@Headers MessageHeaders headers, @Payload String kafkaMessage){

		log.info("header : {}", headers);
		String idempotencyKey = new String((byte[]) headers.get("IdempotencyKey"), StandardCharsets.UTF_8);
		log.info("idempotencyKey : {}", idempotencyKey);

		OrderDto orderDto = convertStringToOrderDto(kafkaMessage);
        service.orderProduct(idempotencyKey, orderDto);
    }

    @KafkaListener(topics = "mosinsa-product-order-cancel")
    public void cancelOrder(String kafkaMessage){
        log.info("kafka message: {}", kafkaMessage);
        OrderDto orderDto = convertStringToOrderDto(kafkaMessage);
        service.cancelOrder(orderDto);
    }

    private OrderDto convertStringToOrderDto(String kafkaMessage) {
        log.info("kafka message: {}", kafkaMessage);

        OrderDto orderDto=null;
        try{
            orderDto = om.readValue(kafkaMessage, OrderDto.class);
            log.info("{}",orderDto);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return orderDto;
    }


}
