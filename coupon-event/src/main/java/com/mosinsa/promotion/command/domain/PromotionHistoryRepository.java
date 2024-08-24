package com.mosinsa.promotion.command.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PromotionHistoryRepository extends Repository<PromotionHistory, Long> {

	PromotionHistory save(PromotionHistory save);
	Optional<PromotionHistory> findById(Long id);

	@Query(value = "select ph from PromotionHistory ph where ph.memberId = :memberId and ph.quest in (:quests) order by ph.lastModifiedDate desc")
	List<PromotionHistory> participatedHistory(@Param("memberId") String memberId, @Param("quests") List<Quest> quests);
}
