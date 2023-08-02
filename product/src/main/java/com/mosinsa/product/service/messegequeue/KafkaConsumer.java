package com.mosinsa.product.service.messegequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.db.dto.OrderDto;
import com.mosinsa.product.db.repository.ProductRepository;
import com.mosinsa.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductRepository repository;
    private final ProductServiceImpl service;
    private final ObjectMapper om;


    @KafkaListener(topics = "mosinsa-product-order")
    public void orderProduct(@Headers MessageHeaders headers, @Payload String kafkaMessage){

		log.info("header : {}", headers);

		OrderDto orderDto = convertStringToOrderDto(kafkaMessage);
        service.orderProduct( orderDto);
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
