package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import com.mosinsa.promotion.command.domain.PromotionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromotionJpaRepository extends JpaRepository<Promotion, PromotionId>, PromotionRepository {

    @Query(value = "select p from Promotion p order by p.period.endDate desc")
    Page<Promotion> findPromotions(Pageable pageable);
}
