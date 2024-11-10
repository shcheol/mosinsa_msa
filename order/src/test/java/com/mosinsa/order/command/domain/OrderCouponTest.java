package com.mosinsa.order.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.order.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCouponTest extends InMemoryJpaTest {

	@Autowired
	OrderRepository repository;

	@Test
	void equalsAndHashcode() {

		OrderCoupon origin = repository.findById(OrderId.of("orderId1")).get().getOrderCoupon();
		OrderCoupon same = repository.findById(OrderId.of("orderId1")).get().getOrderCoupon();
		OrderCoupon protectedConstructor = new OrderCoupon();
		OrderCoupon other = repository.findById(OrderId.of("orderId2")).get().getOrderCoupon();

		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(origin, same, protectedConstructor, other);
		assertThat(b).isTrue();

	}

	@Test
	void filedValues() {
		Order order = repository.findById(OrderId.of("orderId1")).get();
		Order orderFromOrderCoupon = order.getOrderCoupon().getOrder();
		assertThat(order).isEqualTo(orderFromOrderCoupon);
		assertThat(order.getOrderCoupon().getCouponId()).isNotEmpty();
	}

}