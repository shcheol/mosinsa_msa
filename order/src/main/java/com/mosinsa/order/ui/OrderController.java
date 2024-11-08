package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.OrderService;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/order")
//	@IdempotencyApi(storeType = "redisIdempotentKeyStore")
	public ResponseEntity<OrderDetail> orders(@RequestBody CreateOrderRequest orderRequest) {

		OrderDetail orderDto = orderService.order(orderRequest.orderConfirm());
		return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<OrderDetail> cancelOrders(@PathVariable String orderId) {

		OrderDetail cancelOrder = orderService.cancelOrder(orderId);
		return ResponseEntity.ok(cancelOrder);
	}

}
