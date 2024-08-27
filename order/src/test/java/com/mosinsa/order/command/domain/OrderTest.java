package com.mosinsa.order.command.domain;

import com.mosinsa.order.InMemoryJpaTest;
import com.mosinsa.order.common.ex.OrderException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

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
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", 1000, 1));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		Order order1 = Order.create(OrderId.newId(), "cId", orderProducts, shippingInfo, 10000);

		Order protectedConstructor = new Order();
		assertThat(protectedConstructor).isNotEqualTo(order1);
	}

	@Test
	void create_상품_empty() {
		List<OrderProduct> orderProducts = List.of();
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		OrderId orderId = OrderId.newId();
		assertThrows(OrderException.class,
				() -> Order.create(orderId, "customerId", orderProducts,
						shippingInfo, 10000));
	}

	@Test
	void create_상품_null() {
		List<OrderProduct> orderProducts = null;
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		OrderId orderId = OrderId.newId();
		assertThrows(OrderException.class,
				() -> Order.create(orderId, "customerId", orderProducts,
						shippingInfo, 10000));
	}

	@Test
	void create_주문자x() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", 1000, 1));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		OrderId orderId = OrderId.newId();
		assertThrows(OrderException.class,
				() -> Order.create(orderId, "", orderProducts,
						shippingInfo, 10000));
	}


	@Test
	void cancelOrder() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", 1000, 1));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");
		Order order = Order.create(OrderId.newId(), "cId", orderProducts, shippingInfo, 10000);
		order.cancelOrder();
		assertThrows(AlreadyCanceledException.class, order::cancelOrder);

	}
}