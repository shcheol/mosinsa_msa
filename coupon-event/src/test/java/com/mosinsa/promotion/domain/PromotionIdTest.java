package com.mosinsa.promotion.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PromotionIdTest {
    @Test
    void of() {
        String id = "id";
        PromotionId of = PromotionId.of(id);

        assertEquals(of.getId(), id);
    }

    @Test
    void ofEmptyString() {
        String id = "";
        assertThrows(IllegalArgumentException.class, () -> PromotionId.of(id));
    }


    @Test
    void ofNullValue() {
        String id = null;
        assertThrows(IllegalArgumentException.class, () -> PromotionId.of(id));
    }

}