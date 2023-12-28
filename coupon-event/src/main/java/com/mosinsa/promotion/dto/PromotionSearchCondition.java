package com.mosinsa.promotion.dto;

import java.time.LocalDateTime;

public record PromotionSearchCondition(Boolean proceeding, String title, LocalDateTime now) {

	public PromotionSearchCondition {
		if (proceeding == null) {
			proceeding = true;
		}
		if (now == null) {
			now = LocalDateTime.now();
		}
	}
}
