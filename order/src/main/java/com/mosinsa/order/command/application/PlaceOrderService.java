package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.command.domain.*;
import com.mosinsa.order.infra.repository.OrderRepository;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderService {
	private final OrderRepository orderRepository;

	@Transactional
	public OrderDetail order(OrderId orderId, CreateOrderRequest orderRequest) {

		ShippingInfoDto shippingInfoDto = orderRequest.orderConfirm().shippingInfo();
		AddressDto address = shippingInfoDto.address();
		ReceiverDto receiver = shippingInfoDto.receiver();

		Order order = orderRepository.save(
				Order.create(
						orderId,
						orderRequest.orderConfirm().customerId(),
						orderRequest.orderConfirm().couponId(),
						orderRequest.orderConfirm().orderProducts().stream().map(
								orderProduct -> OrderProduct.of(
										orderProduct.productId(),
										orderProduct.price(),
										orderProduct.quantity())
						).toList(),
						ShippingInfo.of(
								Address.of(address.zipCode(), address.address1(), address.address2()),
								Receiver.of(receiver.name(), receiver.phoneNumber()),
								shippingInfoDto.message()
						),
						orderRequest.orderConfirm().totalAmount()
				)
		);

		return new OrderDetail(order);
	}

}
