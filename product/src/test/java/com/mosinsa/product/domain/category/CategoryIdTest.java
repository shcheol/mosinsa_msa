package com.mosinsa.product.domain.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryIdTest {

    @Test
    void idCreateFail() {
        assertThrows(IllegalArgumentException.class, () -> CategoryId.of(null));
        assertThrows(IllegalArgumentException.class, () -> CategoryId.of(""));
    }

    @Test
    void idCreateSuccess() {
        String value = "id";
        CategoryId id = CategoryId.of(value);
        assertThat(id.getId()).isEqualTo(value);
    }


    @Test
    void idEqualsAndHashCode() {
        String value = "id";
        CategoryId idA = CategoryId.of(value);
        CategoryId idB = CategoryId.of(value);

        assertThat(idA).isEqualTo(idB).hasSameHashCodeAs(idB);

        assertThat(idA).isNotEqualTo(null).isNotEqualTo(new TestClass());
    }

    static class TestClass {

    }
}