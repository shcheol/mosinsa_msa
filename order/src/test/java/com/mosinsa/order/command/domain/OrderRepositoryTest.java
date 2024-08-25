package com.mosinsa.order.command.domain;

import com.mosinsa.order.InMemoryJpaTest;
import com.mosinsa.order.code.TestClass;
import com.mosinsa.order.command.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderRepositoryTest extends InMemoryJpaTest {

	@Autowired
	OrderRepository repository;

	@Test
	void equalsAndHashCode() {
		Order orderA = repository.findOrderDetailsById(OrderId.of("orderId1")).get();
		Order orderB = repository.findOrderDetailsById(OrderId.of("orderId1")).get();

		assertThat(orderA).isEqualTo(orderA).isEqualTo(orderB).hasSameHashCodeAs(orderB)
				.isNotEqualTo(null).isNotEqualTo(new TestClass());

		Order orderC = repository.findOrderDetailsById(OrderId.of("orderId2")).get();
		assertThat(orderA).isNotEqualTo(orderC).doesNotHaveSameHashCodeAs(orderC);
	}

	@Test
	void create() {
		Address address = Address.of("zipCode", "address1", "address2");
		Receiver receiver = Receiver.of("myname", "010-xxx-xxxx");
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver, "");

		Order order = Order.create(OrderId.newId(),
				"customerId",
				List.of(OrderProduct.of("productId", 1000, 10)),
				shippingInfo,
				10000);
		Order saveOrder = repository.save(order);

		Order findOrder = repository.findById(saveOrder.getId()).get();

		assertThat(findOrder).isEqualTo(saveOrder);
		assertThat(findOrder.getOrderCoupon().getCouponId()).isEmpty();
		assertThat(findOrder.getOrderProducts()).hasSize(1);
		assertThat(findOrder.getOrderProducts().get(0).getId()).isNotNull();
		assertThat(findOrder.getOrderProducts().get(0).getPrice().getValue()).isEqualTo(1000);
		assertThat(findOrder.getOrderProducts().get(0).getProductId()).isEqualTo("productId");
		assertThat(findOrder.getOrderProducts().get(0).getQuantity()).isEqualTo(10);
		assertThat(findOrder.getOrderProducts().get(0).getOrder()).isEqualTo(findOrder);
		assertThat(findOrder.getCustomerId()).isEqualTo("customerId");
		assertThat(findOrder.getCreatedDate()).isNotNull();
		assertThat(findOrder.getLastModifiedDate()).isNotNull();
		assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.PAYMENT_WAITING);
	}

	@Test
	void cancelFail() {
		Order findOrder3 = repository.findById(OrderId.of("orderId3")).get();
		assertThat(findOrder3.getStatus()).isNotEqualTo(OrderStatus.CANCELED);

		assertThrows(AlreadyShippedException.class, findOrder3::cancelOrder);
	}
}