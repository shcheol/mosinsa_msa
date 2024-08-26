package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.command.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderService {
	private final OrderRepository orderRepository;

	@Transactional
	public Order order(OrderId orderId, OrderConfirmDto orderConfirmDto) {

		ShippingInfoDto shippingInfoDto = orderConfirmDto.shippingInfo();
		AddressDto address = shippingInfoDto.address();
		ReceiverDto receiver = shippingInfoDto.receiver();

		ShippingInfo shippingInfo = ShippingInfo.of(
				Address.of(address.zipCode(), address.address1(), address.address2()),
				Receiver.of(receiver.name(), receiver.phoneNumber()),
				shippingInfoDto.message()
		);
		List<OrderProduct> orderProducts = orderConfirmDto.orderProducts().stream()
				.map(orderProduct -> OrderProduct.of(
						orderProduct.productId(),
						orderProduct.price(),
						orderProduct.quantity())).toList();

		Order order = orderRepository.save(Order.create(orderId,
				orderConfirmDto.customerId(),
				orderProducts,
				shippingInfo,
				orderConfirmDto.totalAmount()));
		order.useCoupon(orderConfirmDto.couponId());
		return order;
	}

}
