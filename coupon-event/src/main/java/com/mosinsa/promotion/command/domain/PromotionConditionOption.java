package com.mosinsa.promotion.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class PromotionConditionOption extends BaseEntity {

	private String optionName;

	@ManyToOne(fetch = FetchType.LAZY)
	private PromotionCondition promotionCondition;

	public static PromotionConditionOption create(String optionName, PromotionCondition promotionCondition) {
		PromotionConditionOption promotionConditionOption = new PromotionConditionOption();
		promotionConditionOption.optionName = optionName;
		promotionConditionOption.promotionCondition = promotionCondition;
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
