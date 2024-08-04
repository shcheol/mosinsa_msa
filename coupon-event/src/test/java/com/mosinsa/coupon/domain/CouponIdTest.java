package com.mosinsa.coupon.domain;

import com.mosinsa.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CouponIdTest {

    @Test
    void of() {
        String id = "id";
        CouponId of = CouponId.of(id);

        assertEquals(of.getId(), id);
    }

    @Test
    void ofEmptyString() {
        String id = "";
        assertThrows(IllegalArgumentException.class, () -> CouponId.of(id));
    }


    @Test
    void ofNullValue() {
        String id = null;
        assertThrows(IllegalArgumentException.class, () -> CouponId.of(id));
    }

    @Test
    void equalsAndHashcode(){
        CouponId origin = CouponId.of("test");
        CouponId same = CouponId.of("test");
        CouponId other = CouponId.of("testxxx");
        CouponId protectedConstructor = new CouponId();

        System.out.println(protectedConstructor.hashCode());
        EqualsAndHashcodeUtils.equalsAndHashcode(origin, same, other, protectedConstructor);
    }

}