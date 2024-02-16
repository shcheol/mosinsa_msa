package com.mosinsa.order.application;

import com.mosinsa.order.common.ex.OrderError;
import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.domain.Order;
import com.mosinsa.order.domain.OrderId;
import com.mosinsa.order.domain.OrderProduct;
import com.mosinsa.order.dto.CreateOrderDto;
import com.mosinsa.order.dto.OrderDetailDto;
import com.mosinsa.order.dto.OrderDto;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.ui.request.CancelOrderRequest;
import com.mosinsa.order.ui.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;

	@Override
	@Transactional
	public OrderDetailDto order(CreateOrderDto createOrderDto) {

		List<OrderProduct> orderProducts = createOrderDto.getMyOrderProducts().stream().map(
				myOrderProduct -> OrderProduct.create(
						myOrderProduct.getProductId(),
						myOrderProduct.getPrice(),
						myOrderProduct.getQuantity())
		).toList();

		if (createOrderDto.isAvailable()) {
			return new OrderDetailDto(
					orderRepository.save(
							Order.create(
									createOrderDto.getCustomerId(),
									createOrderDto.getCouponId(),
									createOrderDto.getDiscountPolicy(),
									createOrderDto.isAvailable(),
									orderProducts)));
		}

		return new OrderDetailDto(
				orderRepository.save(
						Order.create(
								createOrderDto.getCustomerId(),
								orderProducts)));
	}

	@Override
	@Transactional
	public OrderDetailDto cancelOrder(CancelOrderRequest request) {
		Order findOrder = orderRepository.findById(OrderId.of(request.getOrderId()))
				.orElseThrow(() -> new OrderException(OrderError.ORDER_NOT_FOUND));
		findOrder.cancelOrder();
		return new OrderDetailDto(findOrder);
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
