package com.mosinsa.order.command.domain;

import com.mosinsa.order.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderProductTest {

    @Test
    void equalsAndHashCode() {
        OrderProduct actual = OrderProduct.of("productId", 1000, 10);
        OrderProduct expect = OrderProduct.of("productId", 1000, 10);

		OrderProduct protectedConstructor = new OrderProduct();

		OrderProduct a = OrderProduct.of("productIdxxxxxx", 1000, 10);
        OrderProduct b = OrderProduct.of("productId", 1012300, 10);
        OrderProduct c = OrderProduct.of("productId", 1000, 10123);

		boolean b1 = EqualsAndHashcodeUtils.equalsAndHashcode(actual, expect, protectedConstructor, a, b, c);
		assertThat(b1).isTrue();
	}

    @Test
    void invalidQuantity() {

        assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.of("productId", 1000, 0));
    }
}