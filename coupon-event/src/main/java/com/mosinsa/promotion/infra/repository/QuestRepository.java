package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.command.domain.ConditionOption;
import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import com.mosinsa.promotion.command.domain.Quest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {

    @Query(value = "select q from Quest q where q.promotionConditionOption.optionName = :conditionOption")
    List<Quest> findByConditionOption(@Param("conditionOption") ConditionOption conditionOption);

}
