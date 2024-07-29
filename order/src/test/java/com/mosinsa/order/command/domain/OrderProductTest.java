package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.code.TestClass;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderProductTest {

    @Test
    void equalsAndHashCode() {
        OrderProduct actual = OrderProduct.create("productId", 1000, 10);
        OrderProduct expect = OrderProduct.create("productId", 1000, 10);
        assertThat(actual).isEqualTo(actual).isEqualTo(expect).hasSameHashCodeAs(expect)
                .isNotEqualTo(null).isNotEqualTo(new TestClass());
        OrderProduct a = OrderProduct.create("productIdxxxxxx", 1000, 10);
        Assertions.assertThat(actual).isNotEqualTo(a).doesNotHaveSameHashCodeAs(a);
        OrderProduct b = OrderProduct.create("productIdxxx", 1012300, 10);
        Assertions.assertThat(actual).isNotEqualTo(b).doesNotHaveSameHashCodeAs(b);
        OrderProduct c = OrderProduct.create("productIdxxx", 1000, 10123);
        Assertions.assertThat(actual).isNotEqualTo(c).doesNotHaveSameHashCodeAs(c);
    }

    @Test
    void invalidQuantity() {

        assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create("productId", 1000, 0));
    }
}