package com.mosinsa.product.messegequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.dto.OrderDto;
import com.mosinsa.product.repository.ProductRepository;
import com.mosinsa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductRepository repository;

    private final ProductService service;
    private final ObjectMapper om;

    @KafkaListener(topics = "mosinsa-product-order")
    public void orderProduct(String kafkaMessage){
        OrderDto orderDto = convertStringToOrderDto(kafkaMessage);
        service.orderProduct(orderDto);
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
