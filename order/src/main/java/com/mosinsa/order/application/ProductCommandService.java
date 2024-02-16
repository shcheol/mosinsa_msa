package com.mosinsa.order.application;

import com.mosinsa.order.dto.OrderDetailDto;
import com.mosinsa.order.infra.feignclient.*;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

	private final ProductClient productClient;

	public void orderProduct(Map<String, Collection<String>> headers, CreateOrderRequest orderRequest){
		productClient.orderProducts(headers,
				new OrderProductRequests(
						orderRequest.getMyOrderProducts().stream().map(op ->
								new OrderProductRequest(op.getProductId(), op.getQuantity())
						).toList()));
	}


	public void cancelOrderProduct(Map<String, Collection<String>> headers, OrderDetailDto orderDetailDto){
		productClient.cancelOrderProducts(headers,
				new CancelOrderProductRequests(
						orderDetailDto.getOrderProducts().stream().map(op ->
								new CancelOrderProductRequest(op.getProductId(), op.getQuantity())
						).toList()));
	}
}
