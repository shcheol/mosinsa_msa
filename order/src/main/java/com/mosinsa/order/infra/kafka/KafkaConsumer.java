package com.mosinsa.order.infra.kafka;

import com.mosinsa.order.db.OrderStatus;
import com.mosinsa.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mosinsa.order.common.converter.JsonConverter.readValueToOrderDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
	private final OrderService service;

    @Transactional
    @KafkaListener(topics = "${mosinsa.topic.order.rollback}")
    public void orderProductRollback(String kafkaMessage){
        log.info("mosinsa-order-rollback: {}", kafkaMessage);
		service.changeOrderStatus(readValueToOrderDto(kafkaMessage).getOrderId(), OrderStatus.CANCEL);
    }

    @Transactional
    @KafkaListener(topics = "${mosinsa.topic.order.commit}")
    public void orderProductCommit(String kafkaMessage){
        log.info("mosinsa-order-commit: {}", kafkaMessage);
		service.changeOrderStatus(readValueToOrderDto(kafkaMessage).getOrderId(), OrderStatus.REQUEST_SUCCESS);
    }
}
