package com.mosinsa.promotion.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Quest extends BaseEntity {

    @ManyToOne
    private Promotion promotion;

    @ManyToOne
    private PromotionConditionOption promotionConditionOption;

    public static Quest create() {
        Quest quest = new Quest();

        return quest;
    }

    protected Quest() {
    }
}
