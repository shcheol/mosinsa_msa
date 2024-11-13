package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.*;
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
	public Order order(OrderId orderId, OrderInfo orderInfo) {

		ShippingInfoDto shippingInfoDto = orderInfo.shippingInfo();
		AddressDto address = shippingInfoDto.address();
		ReceiverDto receiver = shippingInfoDto.receiver();

		ShippingInfo shippingInfo = ShippingInfo.of(
				Address.of(address.zipCode(), address.address1(), address.address2()),
				Receiver.of(receiver.name(), receiver.phoneNumber()),
				shippingInfoDto.message()
		);
		List<OrderProduct> orderProducts = orderInfo.orderProducts().stream()
				.map(orderProduct -> {
					OrderProduct of = OrderProduct.of(
							orderProduct.id(),
							orderProduct.name(),
							orderProduct.perPrice(),
							orderProduct.quantity(),
							orderProduct.totalPrice());
					of.addProductOptions(orderProduct.options()
							.stream()
							.map(option -> ProductOption.of(option.id(), option.name()))
							.toList());
					of.addCoupons(orderProduct.coupons()
							.stream()
							.map(coupon -> OrderCoupon.of(coupon.id(), coupon.discountPolicy()))
							.toList());
					return of;
				}).toList();

		return orderRepository.save(Order.create(orderId,
				orderInfo.customerInfo().id(),
				orderInfo.orderProducts().stream().map(OrderInfo.OrderProductInfo::totalPrice).reduce(Integer::sum).get(),
				shippingInfo,
				orderProducts));
	}

}
