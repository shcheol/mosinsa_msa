package com.mosinsa.promotion.ui;

import com.mosinsa.promotion.command.domain.Promotion;
import com.mosinsa.promotion.command.domain.PromotionPeriod;
import com.mosinsa.promotion.query.PromotionQueryService;
import com.mosinsa.promotion.query.dto.PromotionDto;
import com.mosinsa.promotion.query.dto.PromotionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public class PromotionQueryServiceStub implements PromotionQueryService {


    LocalDateTime before = LocalDateTime.of(2023, 10, 28, 00, 00);
    LocalDateTime after = LocalDateTime.of(2024, 10, 28, 00, 00);

    @Override
    public PromotionDto getPromotionDetails(String promotionId) {

        return PromotionDto.of(Promotion.create(
                "title", "context", new PromotionPeriod(before, after))
        );
    }

    @Override
    public Page<PromotionDto> findPromotionsByCondition(PromotionSearchCondition condition, Pageable pageable) {
        PromotionDto dto1 = PromotionDto.of(Promotion.create(
                "title", "context", new PromotionPeriod(before, after))
        );
        PromotionDto dto2 = PromotionDto.of(Promotion.create(
                "title", "context", new PromotionPeriod(before, after))
        );

        PageImpl<PromotionDto> page = new PageImpl<>(List.of(dto1, dto2), PageRequest.of(0, 2), 2);
        return page;
    }
}
