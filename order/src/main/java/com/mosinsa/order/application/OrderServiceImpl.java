package com.mosinsa.order.application;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.domain.Order;
import com.mosinsa.order.domain.OrderId;
import com.mosinsa.order.domain.OrderProduct;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.infra.feignclient.CancelOrderProductRequest;
import com.mosinsa.order.infra.feignclient.CustomerClient;
import com.mosinsa.order.infra.feignclient.OrderProductRequest;
import com.mosinsa.order.infra.feignclient.ProductClient;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.ui.request.OrderCreateRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import com.mosinsa.order.ui.response.ResponseCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final ProductClient productClient;
	private final CustomerClient customerClient;


	@Override
	@Transactional
	public OrderDto order(OrderCreateRequest request, Map<String, Collection<String>> authMap) {

		log.info("header map {}", authMap);
		ResponseCustomer customer = customerClient.getCustomer(authMap, request.getCustomerId());

		List<OrderProduct> orderProducts = request.getMyOrderProducts().stream().map(
				myOrderProduct -> OrderProduct.create(
						myOrderProduct.getProductId(),
						myOrderProduct.getPrice(),
						myOrderProduct.getPrice())
		).toList();

		productClient.orderProducts(
				orderProducts.stream().map(op ->
						new OrderProductRequest(op.getProductId(), op.getQuantity())
				).toList());

		Order order = orderRepository.save(Order.create(customer.getId(), orderProducts));
		return new OrderDto(order);
	}

	@Override
	@Transactional
	public void cancelOrder(String customerId, String orderId, Map<String, Collection<String>> authMap) {
		Order findOrder = orderRepository.findById(OrderId.of(orderId))
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));
		Assert.isTrue(Objects.equals(findOrder.getCustomerId(), customerId), "주문한 고객과 동일한 고객이 취소해야합니다.");

		productClient.cancelOrderProducts(
				findOrder.getOrderProducts().stream().map(op ->
						new CancelOrderProductRequest(op.getProductId(), op.getQuantity())
				).toList());

		findOrder.cancelOrder();
	}

	@Override
	public List<OrderDto> getOrderCustomer(String customerId) {

		return orderRepository.findOrderByCustomerIdOrderByCreatedDateDesc(customerId)
				.stream().map(OrderDto::new).toList();
	}

	@Override
	public Page<OrderDto> findOrdersByCondition(SearchCondition condition, Pageable pageable) {
		return orderRepository.findOrdersByCondition(condition, pageable).map(OrderDto::new);
	}

	@Override
	public OrderDto findOrderById(String orderId) {
		return new OrderDto(orderRepository.findById(OrderId.of(orderId))
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND)));
	}

}
