package com.mosinsa.order.ui;

import com.mosinsa.order.application.CouponCommandService;
import com.mosinsa.order.application.CouponQueryService;
import com.mosinsa.order.application.OrderService;
import com.mosinsa.order.application.ProductCommandService;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.dto.CreateOrderDto;
import com.mosinsa.order.dto.OrderDetailDto;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.infra.feignclient.CouponResponse;
import com.mosinsa.order.infra.feignclient.HeaderConst;
import com.mosinsa.order.ui.request.CancelOrderRequest;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.SearchCondition;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	private final CouponQueryService couponQueryService;

	private final CouponCommandService couponCommandService;

	private final ProductCommandService productCommandService;


	@GetMapping
	public ResponseEntity<BaseResponse> findMyOrders(SearchCondition condition, @PageableDefault Pageable pageable) {
		log.info("condition {}", condition);
		Page<OrderDto> orderCustomer = orderService.findMyOrdersByCondition(condition, pageable);
		return GlobalResponseEntity.success(orderCustomer);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<BaseResponse> orderDetails(@PathVariable String orderId) {
		if (!StringUtils.hasText(orderId)) {
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		OrderDetailDto orderDto = orderService.getOrderDetails(orderId);
		return GlobalResponseEntity.success(orderDto);
	}


	@PostMapping
	public ResponseEntity<BaseResponse> orders(@RequestBody CreateOrderRequest orderRequest, HttpServletRequest request) {

		Map<String, Collection<String>> authMap = getAuthMap(request);
		CouponResponse coupon = couponQueryService.getCoupon(authMap, orderRequest.getCouponId());
		productCommandService.orderProduct(authMap, orderRequest);

		CreateOrderDto createOrderDto = new CreateOrderDto(
				orderRequest.getCustomerId(),
				coupon.couponId(),
				coupon.discountPolicy(),
				coupon.available(),
				orderRequest.getMyOrderProducts());
		try {
			OrderDetailDto orderDto = orderService.order(createOrderDto);
			couponCommandService.useCoupon(authMap, coupon.couponId());
			return GlobalResponseEntity.success(HttpStatus.CREATED, orderDto);
		} catch (Exception e) {
			throw new RuntimeException(e);
//			throw new OrderRollbackException(e);
		}

	}

	@PostMapping("/cancel")
	public ResponseEntity<BaseResponse> cancelOrders(@RequestBody CancelOrderRequest cancelRequest, HttpServletRequest request) {

		orderService.cancelOrder(getAuthMap(request), cancelRequest);
		return GlobalResponseEntity.success(cancelRequest.getOrderId());
	}

	private Map<String, Collection<String>> getAuthMap(HttpServletRequest request) {
		Map<String, Collection<String>> authMap = new HashMap<>();
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(auth)) {
			authMap.put(HttpHeaders.AUTHORIZATION, List.of(auth));
		}
		String token = request.getHeader(HeaderConst.REFRESH_TOKEN.getName());
		if (StringUtils.hasText(token)) {
			authMap.put(HeaderConst.REFRESH_TOKEN.getName(), List.of(token));
		}
		return authMap;
	}

}
