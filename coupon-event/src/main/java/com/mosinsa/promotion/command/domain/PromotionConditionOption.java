package com.mosinsa.promotion.command.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PromotionConditionOption extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private ConditionOption optionName;

	@ManyToOne(fetch = FetchType.LAZY)
	private PromotionCondition promotionCondition;

	public static PromotionConditionOption create(ConditionOption optionName) {
		PromotionConditionOption promotionConditionOption = new PromotionConditionOption();
		promotionConditionOption.optionName = optionName;
		return promotionConditionOption;
	}

	protected PromotionConditionOption() {
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
