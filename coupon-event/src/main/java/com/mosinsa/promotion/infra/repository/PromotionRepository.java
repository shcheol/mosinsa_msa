package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromotionRepository extends JpaRepository<Promotion, PromotionId> {

    @Query(value = "select p from Promotion p order by p.period.endDate desc")
    Page<Promotion> findPromotions(Pageable pageable);
}
