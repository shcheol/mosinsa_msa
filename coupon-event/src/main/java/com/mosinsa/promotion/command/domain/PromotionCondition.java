package com.mosinsa.promotion.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class PromotionCondition extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private PromotionConditions conditions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "promotionCondition")
	private List<PromotionConditionOption> promotionConditionOptions = new ArrayList<>();
	public static PromotionCondition create(PromotionConditions conditions) {
		PromotionCondition promotionCondition = new PromotionCondition();
		promotionCondition.conditions = conditions;
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
