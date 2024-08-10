package com.mosinsa.promotion.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
public class PromotionConditionOption extends BaseEntity{


    public static PromotionConditionOption create() {
        PromotionConditionOption promotionConditionOption = new PromotionConditionOption();

        return promotionConditionOption;
    }

    protected PromotionConditionOption() {
    }
}
