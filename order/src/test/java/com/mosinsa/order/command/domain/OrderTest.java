package com.mosinsa.order.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.common.ex.OrderException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest extends InMemoryJpaTest {

	@Autowired
	OrderRepository repository;
	private final Receiver receiver = Receiver.of("", "");
	private final Address address = Address.of("", "", "");

	@Test
	void equalsAndHashCode() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", "",1000, 1, 1000));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		Order order1 = Order.create(OrderId.newId(), "cId", 10000,shippingInfo, orderProducts);

		Order protectedConstructor = new Order();
		assertThat(protectedConstructor).isNotEqualTo(order1);
	}

	@Test
	void create_상품_empty() {
		List<OrderProduct> orderProducts = List.of();
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		OrderId orderId = OrderId.newId();
		assertThrows(OrderException.class,
				() -> Order.create(orderId, "customerId", 10000, shippingInfo, orderProducts));
	}

	@Test
	void create_상품_null() {
		List<OrderProduct> orderProducts = null;
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		OrderId orderId = OrderId.newId();
		assertThrows(OrderException.class,
				() -> Order.create(orderId, "customerId",10000 ,shippingInfo, orderProducts));
	}

	@Test
	void create_주문자x() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", "",1000, 1, 1000));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		OrderId orderId = OrderId.newId();
		assertThrows(OrderException.class,
				() -> Order.create(orderId, "",10000 ,shippingInfo, orderProducts));
	}


	@Test
	void cancelOrder() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", "",1000, 1, 1000));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		Order order = Order.create(OrderId.newId(), "cId",10000 ,shippingInfo, orderProducts);
		order.cancelOrder();
		assertThrows(AlreadyCanceledException.class, order::cancelOrder);

	}
}