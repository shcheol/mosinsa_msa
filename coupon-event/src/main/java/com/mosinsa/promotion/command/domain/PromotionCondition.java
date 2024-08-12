package com.mosinsa.promotion.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class PromotionCondition extends BaseEntity {

	private String conditions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "promotionCondition")
	private List<PromotionConditionOption> promotionConditionOptions = new ArrayList<>();
	public static PromotionCondition create(String conditions) {
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
