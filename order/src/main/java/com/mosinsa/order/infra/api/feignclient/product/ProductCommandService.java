package com.mosinsa.order.infra.api.feignclient.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.dto.OrderProductDto;
import com.mosinsa.order.infra.api.ResponseResult;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductCommandService {

	private final ProductClient productClient;

	public ResponseResult<Void> orderProduct(Map<String, Collection<String>> headers, String orderId, CreateOrderRequest orderRequest) {
//		return ResponseResult.execute(() ->
//				productClient.orderProducts(headers,
//						new OrderProductRequests(orderId, orderRequest.orderConfirm().orderProducts().stream()
//								.map(op -> new OrderProductRequest(op.productId(), op.quantity())).toList())));
		return ResponseResult.execute(() -> {
			OrderProductRequests orderProductRequests = new OrderProductRequests(orderId, orderRequest.orderConfirm().orderProducts().stream()
					.map(op -> new OrderProductRequest(op.productId(), op.quantity())).toList());
			try {
				String body = HttpClient.newBuilder().build()
						.send(HttpRequest.newBuilder().uri(new URI("http://127.0.0.1:8000/product-service/products/order"))
										.POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(orderProductRequests))).build(),
								HttpResponse.BodyHandlers.ofString()).body();
				System.out.println(body);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		});

	}


	public ResponseResult<Void> cancelOrderProduct(Map<String, Collection<String>> headers, List<OrderProductDto> orderProducts) {
		return ResponseResult
				.execute(() -> productClient.cancelOrderProducts(headers,
						new CancelOrderProductRequests(orderProducts.stream()
								.map(op -> new CancelOrderProductRequest(op.productId(), op.quantity())).toList())));
	}
}
