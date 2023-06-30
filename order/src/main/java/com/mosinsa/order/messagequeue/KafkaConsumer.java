package com.mosinsa.order.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.entity.Order;
import com.mosinsa.order.entity.OrderStatus;
import com.mosinsa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final OrderRepository repository;

    private final ObjectMapper om;

    @Transactional
    @KafkaListener(topics = "mosinsa-product-order-rollback")
    public void orderProductRollback(String kafkaMessage){
        log.info("kafka message: {}", kafkaMessage);

        OrderDto orderDto = null;
        try{
            orderDto = om.readValue(kafkaMessage, OrderDto.class);
            log.info("{}",orderDto);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        Long id = orderDto.getId();
        Order order = repository.findById(id).orElseThrow(() -> new IllegalStateException("조회한 주문이 없습니다."));
        order.changeOrderStatus(OrderStatus.CANCEL);

    }

    @Transactional
    @KafkaListener(topics = "mosinsa-product-order-commit")
    public void orderProductCommit(String kafkaMessage){
        log.info("kafka message: {}", kafkaMessage);

        OrderDto orderDto = null;
        try{
            orderDto = om.readValue(kafkaMessage, OrderDto.class);
            log.info("{}",orderDto);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        Long id = orderDto.getId();
        Order order = repository.findById(id).orElseThrow(() -> new IllegalStateException("조회한 주문이 없습니다."));
        order.changeOrderStatus(OrderStatus.REQUEST_SUCCESS);

    }
}
