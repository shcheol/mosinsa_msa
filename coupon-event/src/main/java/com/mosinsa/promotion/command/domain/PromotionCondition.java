package com.mosinsa.promotion.command.domain;

import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class PromotionCondition extends BaseEntity {


    private String conditions;
    public static PromotionCondition create() {
        PromotionCondition promotionCondition = new PromotionCondition();

        return promotionCondition;
    }

    protected PromotionCondition() {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
