package com.mosinsa.order.command.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusTest {

    @Test
    void contains() {
        boolean contains = OrderStatus.contains(OrderStatus.CANCELED);
        assertThat(contains).isTrue();

    }

    @Test
    void containsNull() {
        assertThat(OrderStatus.contains(null)).isFalse();
    }
}