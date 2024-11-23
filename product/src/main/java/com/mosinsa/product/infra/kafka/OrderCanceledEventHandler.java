package com.mosinsa.product.infra.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.product.command.application.ProductService;
import com.mosinsa.product.ui.request.OrderProductRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCanceledEventHandler {

	private final ProductService productService;

	private final ObjectMapper om;

	@KafkaListener(topics = "${mosinsa.topic.order.cancel}")
	public void orderCanceledEvent(String message) throws JsonProcessingException {
		OrderCanceledEvent orderCanceledEvent = om.readValue(message, OrderCanceledEvent.class);

		OrderProductRequests cancelOrderProductRequests = new OrderProductRequests(orderCanceledEvent.id(),
				orderCanceledEvent.orderProducts()
						.stream()
						.map(cop -> new OrderProductRequests.OrderProductDto(cop.id(), cop.quantity(), cop.name(), cop.options()
								.stream()
								.map(option -> new OrderProductRequests.OrderProductDto.ProductOptionsDto(option.id(), option.name()))
								.toList()))
						.toList());


		productService.cancelOrderProduct(orderCanceledEvent.customerInfo().id(), orderCanceledEvent.id(), cancelOrderProductRequests);
	}
}
