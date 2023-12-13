package com.mosinsa.order.ui;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.ui.request.OrderCancelRequest;
import com.mosinsa.order.ui.request.OrderCreateRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.application.OrderService;
import com.mosinsa.order.infra.feignclient.HeaderConst;
import com.mosinsa.order.ui.response.BaseResponse;
import com.mosinsa.order.ui.response.GlobalResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping("/{customerId}/orders")
	public ResponseEntity<BaseResponse> getOrderByCustomer(@PathVariable String customerId) {

		if (!StringUtils.hasText(customerId)) {
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		List<OrderDto> orderCustomer = orderService.getOrderCustomer(customerId);
		return GlobalResponseEntity.success(orderCustomer);
	}

	@GetMapping("/orders")
	public ResponseEntity<BaseResponse> findOrders(SearchCondition condition, @PageableDefault Pageable pageable) {
		log.info("condition {}", condition);
		Page<OrderDto> orderCustomer = orderService.findOrdersByCondition(condition, pageable);
		return GlobalResponseEntity.success(orderCustomer);
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<BaseResponse> getOrder(@PathVariable String orderId) {
		if (!StringUtils.hasText(orderId)) {
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		OrderDto orderDto = orderService.findOrderById(orderId);
		return GlobalResponseEntity.success(orderDto);
	}


	@PostMapping("/orders")
	public ResponseEntity<BaseResponse> orders(@RequestBody OrderCreateRequest orderRequest, HttpServletRequest request) {

		OrderDto orderDto = orderService.order(orderRequest, getAuthMap(request));
		return GlobalResponseEntity.success(HttpStatus.CREATED, orderDto);
	}

	@PatchMapping("/orders")
	public ResponseEntity<BaseResponse> cancelOrders(@RequestBody OrderCancelRequest cancelRequest, HttpServletRequest request) {

		Assert.isTrue(cancelRequest.getCustomerId() != null && !cancelRequest.getCustomerId().isBlank(), "고객 id가 없습니다.");
		Assert.isTrue(cancelRequest.getOrderId() != null, "주문 id가 없습니다.");

		orderService.cancelOrder(cancelRequest.getCustomerId(), cancelRequest.getOrderId(), getAuthMap(request));
		return GlobalResponseEntity.success(cancelRequest.getOrderId());
	}

	private Map<String, Collection<String>> getAuthMap(HttpServletRequest request) {
		Map<String, Collection<String>> authMap = new HashMap<>();
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(header)) {
			authMap.put(HttpHeaders.AUTHORIZATION, List.of(header));
		}
		String header1 = request.getHeader(HeaderConst.REFRESH_TOKEN.getName());
		if (StringUtils.hasText(header1)) {
			authMap.put(HeaderConst.REFRESH_TOKEN.getName(), List.of(header1));
		}
		return authMap;
	}

}
