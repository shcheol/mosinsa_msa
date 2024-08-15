package com.mosinsa.promotion.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Quest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="promotion_id")
    private Promotion promotion;

	@ManyToOne(fetch = FetchType.LAZY)
    private PromotionConditionOption promotionConditionOption;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quest")
	private List<CouponGroup> couponGroupList = new ArrayList<>();

    public static Quest create() {
		return new Quest();
    }

    protected Quest() {
    }

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
