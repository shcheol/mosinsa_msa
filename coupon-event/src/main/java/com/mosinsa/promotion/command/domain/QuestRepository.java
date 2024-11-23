package com.mosinsa.promotion.command.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestRepository extends Repository<Quest, Long> {

	Quest save(Quest quest);

	Optional<Quest> findById(Long id);

	@Query(value = "select q from Quest q join fetch q.promotion where q.promotionConditionOption.optionName = :conditionOption and q.promotion.id = :id")
	List<Quest> findByConditionOption(@Param("id") PromotionId id,@Param("conditionOption") ConditionOption conditionOption);

}
