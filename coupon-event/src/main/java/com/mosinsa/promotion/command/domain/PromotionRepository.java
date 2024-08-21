package com.mosinsa.promotion.command.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface PromotionRepository extends Repository<Promotion, PromotionId> {

    @Query(value = "select p from Promotion p order by p.period.endDate desc")
	Page<Promotion> findPromotions(Pageable pageable);

	Optional<Promotion> findById(PromotionId id);

	Promotion save(Promotion promotion);
}