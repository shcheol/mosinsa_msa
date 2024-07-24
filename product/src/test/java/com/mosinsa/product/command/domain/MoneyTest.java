package com.mosinsa.product.command.domain;

import com.mosinsa.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    void create(){
        Money of = Money.of(1);
        assertThat(of.getValue()).isEqualTo(1);
    }

    @Test
    void createFail(){
        assertThrows(InvalidMoneyException.class, () -> Money.of(0));
    }

    @Test
    void multiply(){
        Money of = Money.of(100);
        Money multiply = of.multiply(20);
        assertThat(multiply.getValue()).isEqualTo(2000);
    }

    @Test
    void multiplyFail(){
        Money of = Money.of(100);
        assertThrows(InvalidMoneyException.class, () -> of.multiply(0));
    }
    @Test
    void idEqualsAndHashCode(){
        int value = 100;
        Money moneyA = Money.of(value);
        Money moneyB = Money.of(value);

        assertThat(moneyA).isEqualTo(moneyA).isEqualTo(moneyB).hasSameHashCodeAs(moneyB)
                .isNotEqualTo(null).isNotEqualTo(new TestClass());
        Money moneyC = Money.of(333);
        assertThat(moneyA).isNotEqualTo(moneyC);
    }
}