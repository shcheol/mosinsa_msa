package com.mosinsa.order.command.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductTest {

    @Test
    void equalsAndHashCode() {
        OrderProduct actual = OrderProduct.create("productId", 1000, 10);
        OrderProduct expect = OrderProduct.create("productId", 1000, 10);
        Assertions.assertThat(actual).isEqualTo(expect)
                .hasSameHashCodeAs(expect);
    }

    @Test
    void invalidQuantity() {

        assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create("productId", 1000, 0));
    }
}