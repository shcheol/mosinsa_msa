package com.mosinsa.order.command.domain;

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
    void createFail(){
        assertThrows(IllegalArgumentException.class, () -> OrderId.of(""));
        assertThrows(IllegalArgumentException.class, () -> OrderId.of(null));
    }

    @Test
    void equalsAndHashCode(){
        OrderId id1 = OrderId.of("id");
        OrderId id2 = OrderId.of("id");
        assertThat(id1).isEqualTo(id2)
				.hasSameHashCodeAs(id2);
    }

}