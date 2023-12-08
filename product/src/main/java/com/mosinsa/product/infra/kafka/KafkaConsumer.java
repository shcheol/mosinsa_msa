package com.mosinsa.product.infra.kafka;

import com.mosinsa.product.application.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.mosinsa.product.common.converter.JsonConverter.readValueToOrderDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ProductServiceImpl service;

    @KafkaListener(topics = "${mosinsa.topic.order.request}")
    public void orderProduct(String kafkaMessage){
        service.orderProduct(readValueToOrderDto(kafkaMessage));
    }

    @KafkaListener(topics = "${mosinsa.topic.order.cancel}")
    public void cancelOrder(String kafkaMessage){
        service.cancelOrder(readValueToOrderDto(kafkaMessage));
    }


}
