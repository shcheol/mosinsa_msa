package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.common.argumentresolver.CustomerInfo;
import com.mosinsa.order.common.argumentresolver.Login;
import com.mosinsa.order.query.application.OrderQueryService;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.query.application.dto.OrderSummary;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class ViewOrderController {

	private final OrderQueryService orderQueryService;


	@GetMapping
	public ResponseEntity<Page<OrderSummary>> findMyOrders(SearchCondition condition, @PageableDefault Pageable pageable) {
		log.info("condition {}", condition);
		Page<OrderSummary> orderCustomer = orderQueryService.findMyOrdersByCondition(condition, pageable);
		return ResponseEntity.ok(orderCustomer);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDetail> orderDetails(@PathVariable String orderId) {
		OrderDetail orderDto = orderQueryService.getOrderDetails(orderId);
		return ResponseEntity.ok(orderDto);
	}

	@PostMapping("/orderConfirm")
	public ResponseEntity<OrderConfirmDto> orderConfirm(@RequestBody OrderConfirmRequest orderConfirmRequest,
														@Login CustomerInfo customerInfo) {

		log.info("{}", orderConfirmRequest);
		OrderConfirmDto orderConfirmDto = orderQueryService.orderConfirm(customerInfo, orderConfirmRequest);

		return ResponseEntity.ok(orderConfirmDto);
	}
}
