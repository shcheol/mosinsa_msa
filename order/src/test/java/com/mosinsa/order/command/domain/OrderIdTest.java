package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.code.TestClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderIdTest {

    @Test
    void create(){
        String value = "id";
        OrderId of = OrderId.of(value);
        assertThat(of.getId()).isEqualTo(value);
    }

    @Test
    void idCreateFail(){
        assertThrows(IllegalArgumentException.class, () -> OrderId.of(null));
        assertThrows(IllegalArgumentException.class, () -> OrderId.of(""));
    }

    @Test
    void idCreateSuccess(){
        String value = "id";
        OrderId id = OrderId.of(value);
        assertThat(id.getId()).isEqualTo(value);
    }


    @Test
    void idEqualsAndHashCode() {
        String value = "id";
        OrderId idA = OrderId.of(value);
        OrderId idB = OrderId.of(value);

        assertThat(idA).isEqualTo(idA).isEqualTo(idB).hasSameHashCodeAs(idB)
                .isNotEqualTo(null).isNotEqualTo(new TestClass());
        OrderId idC = OrderId.of("idxx");
        assertThat(idA).isNotEqualTo(idC).doesNotHaveSameHashCodeAs(idC);

        OrderId protectedConstructor = new OrderId();
        assertThat(protectedConstructor.hashCode()).isZero();
    }

}