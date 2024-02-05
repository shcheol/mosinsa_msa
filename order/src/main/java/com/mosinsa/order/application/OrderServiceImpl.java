package com.mosinsa.order.application;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.domain.Order;
import com.mosinsa.order.domain.OrderId;
import com.mosinsa.order.domain.OrderProduct;
import com.mosinsa.order.dto.OrderDetailDto;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.infra.feignclient.*;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.ui.request.CancelOrderRequest;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private final CouponClient couponClient;


	@Override
	@Transactional
	public OrderDetailDto order(Map<String, Collection<String>> headers, CreateOrderRequest request) {

		List<OrderProduct> orderProducts = request.getMyOrderProducts().stream().map(
				myOrderProduct -> OrderProduct.create(
						myOrderProduct.getProductId(),
						myOrderProduct.getPrice(),
						myOrderProduct.getQuantity())
		).toList();

		productClient.orderProducts(headers,
				new OrderProductRequests(
						orderProducts.stream().map(op ->
								new OrderProductRequest(op.getProductId(), op.getQuantity())
						).toList()));

		String couponId = request.getCouponId();
		Order order = null;
		if (StringUtils.hasText(couponId)) {
			CouponResponse coupon = couponClient.getCoupon(headers, couponId);
			order = orderRepository.save(
					Order.create(
							request.getCustomerId(),
							coupon.couponId(),
							coupon.discountPolicy(),
							coupon.available(),
							orderProducts));
		} else {
			order = orderRepository.save(
					Order.create(
							request.getCustomerId(),
							orderProducts));
		}

		return new OrderDetailDto(order);
	}

	@Override
	@Transactional
	public void cancelOrder(Map<String, Collection<String>> headers, CancelOrderRequest request) {
		Order findOrder = orderRepository.findById(OrderId.of(request.getOrderId()))
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));

		productClient.cancelOrderProducts(headers,
				new CancelOrderProductRequests(
						findOrder.getOrderProducts().stream().map(op ->
								new CancelOrderProductRequest(op.getProductId(), op.getQuantity())
						).toList()));

		findOrder.cancelOrder();
	}

	@Override
	public Page<OrderDto> findMyOrdersByCondition(SearchCondition condition, Pageable pageable) {
		if (!StringUtils.hasText(condition.getCustomerId())) {
			throw new OrderException(OrderError.VALIDATION_ERROR);
		}
		return orderRepository.findOrdersByCondition(condition, pageable).map(OrderDto::new);
	}

	@Override
	public OrderDetailDto getOrderDetails(String orderId) {
		return new OrderDetailDto(orderRepository.findById(OrderId.of(orderId))
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND)));
	}

}
