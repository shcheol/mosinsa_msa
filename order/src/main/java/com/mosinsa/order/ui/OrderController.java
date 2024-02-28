package com.mosinsa.order.ui;

import com.mosinsa.order.application.OrderService;
import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.common.ex.OrderRollbackException;
import com.mosinsa.order.application.dto.CreateOrderDto;
import com.mosinsa.order.application.dto.OrderDetailDto;
import com.mosinsa.order.application.dto.OrderDto;
import com.mosinsa.order.infra.feignclient.HeaderConst;
import com.mosinsa.order.infra.feignclient.coupon.CouponCommandService;
import com.mosinsa.order.infra.feignclient.coupon.CouponQueryService;
import com.mosinsa.order.infra.feignclient.coupon.SimpleCouponResponse;
import com.mosinsa.order.infra.feignclient.product.ProductCommandService;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.MyOrderProduct;
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
		SimpleCouponResponse coupon = couponQueryService.couponCheck(authMap, orderRequest.getCouponId());
		productCommandService.orderProduct(authMap, orderRequest);

		try {
			OrderDetailDto orderDto = orderService.order(new CreateOrderDto(
					orderRequest.getCustomerId(),
					coupon.couponId(),
					coupon.discountPolicy(),
					coupon.available(),
					orderRequest.getMyOrderProducts()));

			couponCommandService.useCoupon(authMap, orderDto.getCouponId());
			return GlobalResponseEntity.success(HttpStatus.CREATED, orderDto);
		} catch (Exception e) {
			log.error("order fail => rollback product stock, coupon use");

			try {
				productCommandService.cancelOrderProduct(authMap, orderRequest.getMyOrderProducts());
				couponCommandService.cancelCoupon(authMap, coupon.couponId());
			} catch (Exception ex) {
				//TODO: kafka 이용 후처리

			}
			throw new OrderRollbackException(e);
		}

	}

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<BaseResponse> cancelOrders(@PathVariable String orderId, HttpServletRequest request) {

		Map<String, Collection<String>> authMap = getAuthMap(request);

		OrderDetailDto cancelOrder = orderService.cancelOrder(orderId);

		try {
			productCommandService.cancelOrderProduct(authMap,
					cancelOrder.getOrderProducts().stream().map(
							op -> new MyOrderProduct(op.getProductId(), op.getPrice(), op.getQuantity())).toList());
			couponCommandService.cancelCoupon(authMap, cancelOrder.getCouponId());
		} catch (Exception e) {
			//TODO: kafka 이용 후처리
			// 주문 취소 성공, 외부 api 호출(상품 수량 증가) 실패
			// 호출을 주문 취소 이후에하는 이유는 취소에 주문 실패했을 때 롤백요청하는 사이에 타 사용자가 수량 가져갈 가능성
		}

		return GlobalResponseEntity.success(orderId);
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
