package com.mosinsa.promotion.domain;

import com.mosinsa.common.event.Events;
import com.mosinsa.coupon.domain.CouponDetails;
import com.mosinsa.coupon.domain.DiscountPolicy;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "promotion")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {

    @EmbeddedId
    private PromotionId promotionId;
    @Nonnull
    private String title;

    @Column(name = "contexts")
    private String context;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private DiscountPolicy discountPolicy;

    @Embedded
    private PromotionPeriod period;

    public static Promotion create(String title, String context, int quantity, DiscountPolicy discountPolicy, PromotionPeriod period, CouponDetails details) {
        Promotion promotion = new Promotion();
        promotion.setPromotionId(PromotionId.newId());
        promotion.setTitle(title);
        promotion.setContext(context);
        promotion.setQuantity(quantity);
        promotion.setDiscountPolicy(discountPolicy);
        promotion.setPeriod(period);
        Events.raise(new PromotionCreatedEvent(promotion.getPromotionId(), promotion.getQuantity(), details));
        return promotion;
    }

    private void setPromotionId(PromotionId promotionId) {
        this.promotionId = promotionId;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setContext(String context) {
        this.context = context;
    }

    private void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    private void setPeriod(PromotionPeriod period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Promotion promotion = (Promotion) o;
        return promotionId != null && Objects.equals(promotionId, promotion.promotionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotionId);
    }
}
