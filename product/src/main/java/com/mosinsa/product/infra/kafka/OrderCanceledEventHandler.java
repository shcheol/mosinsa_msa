package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.command.application.ProductServiceImpl;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCanceledEventHandler {

	private final ProductServiceImpl productServiceImpl;

	@KafkaListener(topics = "${mosinsa.topic.order.cancel}")
	public void orderCanceledEvent(String message) throws JsonProcessingException {
		OrderCanceledEvent orderCanceledEvent = new ObjectMapper().readValue(message, OrderCanceledEvent.class);

		List<CancelOrderProductRequest> cancelOrderProductRequests = orderCanceledEvent.orderProducts().stream()
				.map(orderedProduct -> new CancelOrderProductRequest(orderedProduct.productId(), orderedProduct.quantity()))
				.toList();

		productServiceImpl.cancelOrderProduct(orderCanceledEvent.customerId(), orderCanceledEvent.orderId(), cancelOrderProductRequests);
	}
}
