package com.mosinsa.order.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.order.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderProductTest extends InMemoryJpaTest {

	@Autowired
	OrderRepository repository;

    @Test
    void equalsAndHashCode() {
		OrderProduct actual = repository.findById(OrderId.of("orderId1")).get().getOrderProducts().get(0);
        OrderProduct expect =  repository.findById(OrderId.of("orderId1")).get().getOrderProducts().get(0);

		OrderProduct protectedConstructor = new OrderProduct();

		OrderProduct a = repository.findById(OrderId.of("orderId2")).get().getOrderProducts().get(0);

		boolean b1 = EqualsAndHashcodeUtils.equalsAndHashcode(actual, expect, protectedConstructor, a);
		assertThat(b1).isTrue();
	}

    @Test
    void invalidQuantity() {

        assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.of("productId", "",1000, 0, 1000));
    }
}