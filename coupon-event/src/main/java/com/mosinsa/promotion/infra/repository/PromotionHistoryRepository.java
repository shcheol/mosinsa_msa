package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.command.domain.PromotionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionHistoryRepository extends JpaRepository<PromotionHistory, Long> {

	@Query(value = "select ph from PromotionHistory ph where ph.memberId = :memberId order by ph.lastModifiedDate desc")
	List<PromotionHistory> participatedHistory(@Param("memberId") String memberId);
}
