package com.mosinsa.order.command.application;

import com.mosinsa.order.command.domain.Order;
import com.mosinsa.order.command.domain.OrderId;
import com.mosinsa.order.command.domain.OrderProduct;
import com.mosinsa.order.command.domain.ShippingInfo;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderService {
	private final OrderRepository orderRepository;

	@Transactional
	public OrderDetail order(String idempotentKey, CreateOrderRequest orderRequest) {

		Optional<Order> byId = orderRepository.findOrderDetailsById(OrderId.of(idempotentKey));
		if (byId.isPresent()){
			log.info("duplicate order request, return origin data");
			return new OrderDetail(byId.get());
		}

		Order order = orderRepository.save(
				Order.create(idempotentKey,
						orderRequest.orderConfirm().customerId(),
						orderRequest.orderConfirm().couponId(),
						orderRequest.orderConfirm().orderProducts().stream().map(
								orderProduct -> OrderProduct.create(
										orderProduct.productId(),
										orderProduct.price(),
										orderProduct.quantity())
						).toList(),
						ShippingInfo.of(orderRequest.orderConfirm().shippingInfo()),
						orderRequest.orderConfirm().totalAmount()));

		return new OrderDetail(order);
	}

}
