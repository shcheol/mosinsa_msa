package com.mosinsa.review.command.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class ReviewIdTest {

    @Test
    void idCreateFail(){
        assertThrows(IllegalArgumentException.class, () -> ReviewId.of(null));
        assertThrows(IllegalArgumentException.class, () -> ReviewId.of(""));
    }

    @Test
    void idCreateSuccess(){
        String value = "id";
        ReviewId id = ReviewId.of(value);
        assertThat(id.getId()).isEqualTo(value);
    }


    @Test
    void idEqualsAndHashCode(){
        String value = "id";
        ReviewId idA = ReviewId.of(value);
        ReviewId idB = ReviewId.of(value);

        assertThat(idA).isEqualTo(idA).isEqualTo(idB).hasSameHashCodeAs(idB)
                .isNotEqualTo(null).isNotEqualTo(new TestClass());

        ReviewId idC = ReviewId.of("idxx");
        assertThat(idA).isNotEqualTo(idC).doesNotHaveSameHashCodeAs(idC);
    }
    static class TestClass{

    }

}