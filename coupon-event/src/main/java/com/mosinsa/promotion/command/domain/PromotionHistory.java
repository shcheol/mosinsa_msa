package com.mosinsa.promotion.command.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class PromotionHistory extends BaseEntity {

    @Nonnull
    private String memberId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Quest quest;

    public static PromotionHistory of(String memberId, Quest quest) {
        PromotionHistory promotionHistory = new PromotionHistory();
        promotionHistory.memberId = memberId;
		promotionHistory.quest = quest;
        return promotionHistory;
    }

    protected PromotionHistory() {
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
