package com.mosinsa.promotion.domain;

import com.mosinsa.common.exception.CouponException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionPeriodTest {

    LocalDateTime before = LocalDateTime.of(2023, 10, 28, 00, 00);
    LocalDateTime after = LocalDateTime.of(2024, 10, 28, 00, 00);

    @Test
    @DisplayName("시작과 종료날짜가 동일")
    void constructPeriodInvalidDate1(){
        assertThrows(CouponException.class, () ->new PromotionPeriod(before, before));
    }

    @Test
    @DisplayName("종료 날짜가 시작날보다 빠름")
    void constructPeriodInvalidDate2(){
        assertThrows(CouponException.class, () ->new PromotionPeriod(after, before));
    }

    @Test
    void constructStartDateNull(){
        assertThrows(CouponException.class, () ->new PromotionPeriod(null,after));
    }
    @Test
    void constructEndDateNull(){
        assertThrows(CouponException.class, () ->new PromotionPeriod(before,null));
    }

    @Test
    void equalsAndHashCode(){
        PromotionPeriod period1 = new PromotionPeriod(before,after);
        PromotionPeriod period2 = new PromotionPeriod(before,after);

		assertThat(period1).hasSameHashCodeAs(period2);
		assertEquals(period1, period2);
		assertNotSame(period1, period2);
    }
	@Test
	void equalsAndHashCodeDiff(){
		PromotionPeriod period1 = new PromotionPeriod(before,after);
		PromotionPeriod period2 = new PromotionPeriod(before, LocalDateTime.of(2024, 10, 28, 00, 10));

		assertNotEquals(period1.hashCode(), period2.hashCode());
		assertNotEquals(period1, period2);
		assertNotSame(period1, period2);
	}

}