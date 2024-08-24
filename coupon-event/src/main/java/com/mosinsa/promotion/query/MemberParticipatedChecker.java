package com.mosinsa.promotion.query;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;
import com.mosinsa.promotion.command.domain.PromotionHistoryRepository;
import com.mosinsa.promotion.command.domain.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberParticipatedChecker {

	private final PromotionHistoryRepository repository;

	private final List<DateUnitStrategy> dateUnitStrategies;

	public boolean isMemberParticipated(String memberId, List<Quest> quests, DateUnit dateUnit) {

		List<PromotionHistory> promotionHistories = repository.participatedHistory(memberId, quests);

		return dateUnitStrategies.stream()
				.filter(dateUnitStrategy -> dateUnitStrategy.isSupport(dateUnit)).findAny()
				.orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
				.isParticipated(promotionHistories);
	}
}
