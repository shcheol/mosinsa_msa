package com.mosinsa.promotion.domain;

import com.mosinsa.coupon.command.domain.CouponDetails;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "promotion")
@Getter
public class Promotion {

    @EmbeddedId
    private PromotionId id;
    @Nonnull
    private String title;

    @Column(name = "contexts")
    private String context;

    private int stock;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    @Embedded
    private PromotionPeriod period;

    public static Promotion create(String title, String context, int stock, DiscountPolicy discountPolicy, PromotionPeriod period) {
        Promotion promotion = new Promotion();
        promotion.id = PromotionId.newId();
        promotion.title = title;
        promotion.context = context;
        promotion.stock = stock;
        promotion.discountPolicy =discountPolicy;
        promotion.period = period;
        return promotion;
    }

	protected Promotion() {
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promotion promotion)) return false;
        return Objects.equals(id, promotion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
