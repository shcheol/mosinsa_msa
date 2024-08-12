package com.mosinsa.promotion.command.application;

import com.mosinsa.promotion.command.application.dto.CreatePromotionRequest;
import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionId;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository repository;

    @Override
    @Transactional
    public PromotionId registerPromotion(CreatePromotionRequest request) {
        Promotion promotion = Promotion.create(request.title(),
                request.context(),
				request.dateUnit(),
                request.period());
        return repository.save(promotion).getId();
    }

}
