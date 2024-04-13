package com.mosinsa.order.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void getValue() {
        Money of = Money.of(2);
        assertThat(of.getValue()).isEqualTo(2);
    }

    @Test
    void of() {
        Money of = Money.of(2);
        assertThat(of.getValue()).isEqualTo(2);

        assertThrows(InvalidMoneyException.class, ()-> Money.of(-1));
    }

    @Test
    void multiply() {
        Money three = Money.of(3);
        assertThat(three.multiply(2).getValue()).isEqualTo(6);
    }

    @Test
    void minus() {
        Money three = Money.of(3);
        assertThat(three.minus(2)).isEqualTo(Money.of(1));
    }

    @Test
    void minus_fail_invalid_value() {
        Money three = Money.of(3);
        assertThrows(InvalidMoneyException.class, ()-> three.minus(-3));
    }

    @Test
    void minus_fail_invalid_result() {
        Money three = Money.of(3);
        assertThrows(InvalidMoneyException.class, ()-> three.minus(4));
    }
}