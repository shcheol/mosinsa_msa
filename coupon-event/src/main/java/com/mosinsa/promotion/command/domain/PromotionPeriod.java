package com.mosinsa.promotion.command.domain;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Getter
public class PromotionPeriod {

	private LocalDateTime startDate;
	private LocalDateTime endDate;

	protected PromotionPeriod() {
	}

	public PromotionPeriod(LocalDateTime startDate, LocalDateTime endDate) {
		validateDates(startDate, endDate);
		this.startDate = startDate;
		this.endDate = endDate;
	}


	public boolean isProceeding(LocalDateTime now){
		return now.isAfter(this.startDate) && now.isBefore(this.endDate);
	}

	private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
		if (startDate == null || endDate == null || startDate.isEqual(endDate) || startDate.isAfter(endDate)) {
			throw new CouponException(CouponError.INVALID_PERIOD_INPUT);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PromotionPeriod that)) return false;
		return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(startDate, endDate);
	}
}
