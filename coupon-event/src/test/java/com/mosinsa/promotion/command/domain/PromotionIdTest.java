package com.mosinsa.promotion.command.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.promotion.command.domain.PromotionId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void equalsAndHashcode(){
        PromotionId origin = PromotionId.of("test");
        PromotionId same = PromotionId.of("test");
        PromotionId other = PromotionId.of("testxxx");
        PromotionId protectedConstructor = new PromotionId();

        assertThat(EqualsAndHashcodeUtils.equalsAndHashcode(origin, same, protectedConstructor, other)).isTrue();
    }

}