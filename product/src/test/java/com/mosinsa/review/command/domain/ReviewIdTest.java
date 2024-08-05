package com.mosinsa.review.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.code.TestClass;
import com.mosinsa.product.command.domain.ProductId;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    void idEqualsAndHashCode() {
        String value = "id";
        ReviewId idA = ReviewId.of(value);
        ReviewId idB = ReviewId.of(value);
        ReviewId idC = ReviewId.of("idxx");
        ReviewId protectedConstructor = new ReviewId();

		EqualsAndHashcodeUtils.equalsAndHashcode(idA,idB, protectedConstructor, idC);
    }

}