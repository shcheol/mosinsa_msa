package com.mosinsa.category;

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
        CategoryId protectedConstructor = new CategoryId();

        assertThat(idA).isEqualTo(idA).isEqualTo(idB).hasSameHashCodeAs(idB)
                .isNotEqualTo(null).isNotEqualTo(new TestClass())
                .doesNotHaveSameHashCodeAs(protectedConstructor);
        assertThat(protectedConstructor.hashCode()).isZero();

    }

    static class TestClass {

    }
}