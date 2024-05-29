package com.mosinsa.order.command.application;

import com.mosinsa.order.infra.kafka.KafkaEvents;
import com.mosinsa.order.infra.kafka.OrderCanceledEvent;
import com.mosinsa.order.query.application.dto.OrderDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCancelTemplate {

	private final CancelOrderService cancelOrderService;

	@Value("${mosinsa.topic.order.cancel}")
	public String orderCancelTopic;

	public OrderDetail cancelOrder(String orderId) {

		OrderDetail cancelOrder = cancelOrderService.cancelOrder(orderId);

		KafkaEvents.raise(orderCancelTopic,
				new OrderCanceledEvent(cancelOrder.getOrderId(),
						cancelOrder.getCustomerId(),
						cancelOrder.getCouponId(),
						cancelOrder.getOrderProducts()));

		return cancelOrder;
	}
}
