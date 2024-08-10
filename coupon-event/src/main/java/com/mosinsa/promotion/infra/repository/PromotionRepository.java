package com.mosinsa.promotion.infra.repository;

import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, PromotionId>, CustomPromotionRepository {
}
