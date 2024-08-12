package com.mosinsa.promotion.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Quest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;

	@ManyToOne(fetch = FetchType.LAZY)
    private PromotionConditionOption promotionConditionOption;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quest")
	private List<CouponGroup> couponGroupList = new ArrayList<>();

    public static Quest create() {
        Quest quest = new Quest();

        return quest;
    }

    protected Quest() {
    }
}
