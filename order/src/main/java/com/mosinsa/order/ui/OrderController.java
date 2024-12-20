package com.mosinsa.order.ui;

import com.mosinsa.common.argumentresolver.CustomerInfo;
import com.mosinsa.common.argumentresolver.Login;
import com.mosinsa.order.command.application.OrderService;
import com.mosinsa.order.command.application.dto.CancelOrderInfo;
import com.mosinsa.order.command.application.dto.OrderInfo;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.OrderRequest;
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
	public ResponseEntity<OrderDetail> orders(@Login CustomerInfo customerInfo, @RequestBody OrderRequest orderRequest) {

		OrderInfo orderInfo = OrderInfo.builder()
				.customerInfo(customerInfo)
				.orderProducts(orderRequest.myOrderProducts()
						.stream()
						.map(myOrderProduct ->
								new OrderInfo.OrderProductInfo(myOrderProduct.id(), myOrderProduct.name(), myOrderProduct.quantity(), myOrderProduct.perPrice(), myOrderProduct.totalPrice(),
										myOrderProduct.options()
												.stream()
												.map(option -> new OrderInfo.OrderProductInfo.ProductOptionsDto(option.id(), option.name()))
												.toList(),
										myOrderProduct.coupons()
												.stream()
												.map(coupon -> new OrderInfo.OrderProductInfo.CouponDto(coupon.id(), coupon.discountPolicy()))
												.toList())
						).toList())
				.shippingInfo(orderRequest.shippingInfo())
				.build();
		OrderDetail orderDto = orderService.order(orderInfo);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<OrderDetail> cancelOrders(@Login CustomerInfo customerInfo, @PathVariable("orderId") String orderId) {

		CancelOrderInfo cancelOrderInfo = new CancelOrderInfo(customerInfo, orderId);
		OrderDetail cancelOrder = orderService.cancelOrder(cancelOrderInfo);
		return ResponseEntity.ok(cancelOrder);
	}

}
