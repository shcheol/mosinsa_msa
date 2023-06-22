package com.mosinsa.product.messegequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.dto.OrderProductDto;
import com.mosinsa.product.entity.Product;
import com.mosinsa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductRepository repository;

    private final ObjectMapper om;

    @Transactional
    @KafkaListener(topics = "mosinsa-product-order")
    public void orderProduct(String kafkaMessage){
        log.info("kafka message: {}", kafkaMessage);

        OrderProductDto orderProductDto = null;
        try{
            orderProductDto = om.readValue(kafkaMessage, OrderProductDto.class);
            log.info("{}",orderProductDto);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        assert orderProductDto != null;
        int orderCount = orderProductDto.getOrderCount();

        log.info("orderCount = {}", orderCount);
        Product product = repository.findById(orderProductDto.getProductDto().getProductId()).orElseThrow(() -> new IllegalArgumentException("조회한 상품이 없습니다."));

        product.removeStock(orderCount);
    }

    @Transactional
    @KafkaListener(topics = "mosinsa-product-cancel")
    public void cancelOrder(String kafkaMessage){
        log.info("kafka message: {}", kafkaMessage);

        OrderProductDto orderProductDto = null;
        try{
            orderProductDto = om.readValue(kafkaMessage, OrderProductDto.class);
            log.info("{}",orderProductDto);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        assert orderProductDto != null;
        int orderCount = orderProductDto.getOrderCount();

        log.info("orderCount = {}", orderCount);
        Product product = repository.findById(orderProductDto.getProductDto().getProductId()).orElseThrow(() -> new IllegalArgumentException("조회한 상품이 없습니다."));

        product.addStock(orderCount);
    }
}
