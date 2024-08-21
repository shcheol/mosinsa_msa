package com.mosinsa.promotion.query;

import com.mosinsa.promotion.command.domain.DateUnit;
import com.mosinsa.promotion.command.domain.PromotionHistory;
import com.mosinsa.promotion.command.domain.Quest;
import com.mosinsa.promotion.infra.repository.PromotionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberParticipatedChecker {

	private final PromotionHistoryRepository repository;

	public boolean isMemberParticipated(String memberId, List<Quest> quests, DateUnit dateUnit) {

		List<PromotionHistory> promotionHistories = repository.participatedHistory(memberId, quests);

		if (promotionHistories.isEmpty()) {
			return false;
		}

		if (dateUnit.equals(DateUnit.ONCE)) {
			return true;
		}

		if (dateUnit.equals(DateUnit.DAILY)) {
			LocalDateTime lastParticipatedDateTime = promotionHistories.get(0).getLastModifiedDate();
			LocalDate lastParticipatedDate = LocalDate.of(lastParticipatedDateTime.getYear(), lastParticipatedDateTime.getMonth(), lastParticipatedDateTime.getDayOfMonth());
			return !lastParticipatedDate.isBefore(LocalDate.now().minusDays(1));
		}

		return false;
	}
}
