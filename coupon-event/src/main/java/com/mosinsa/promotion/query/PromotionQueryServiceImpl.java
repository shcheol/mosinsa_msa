package com.mosinsa.promotion.query;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.command.domain.*;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import com.mosinsa.promotion.infra.repository.QuestRepository;
import com.mosinsa.promotion.query.dto.PromotionDetails;
import com.mosinsa.promotion.query.dto.PromotionSummary;
import com.mosinsa.promotion.query.dto.QuestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionQueryServiceImpl implements PromotionQueryService {

	private final PromotionRepository repository;

	private final QuestRepository questRepository;
	private final ConditionOptionFinder optionFinder;

	@Override
	@Transactional
	public PromotionDetails getPromotionDetails(String promotionId, String memberId) {
		Promotion promotion = repository.findById(PromotionId.of(promotionId))
				.orElseThrow(() -> new CouponException(CouponError.NOT_FOUND));
		PromotionCondition promotionCondition = promotion.getPromotionCondition();
		PromotionConditions conditions = promotionCondition.getConditions();

		ConditionStrategy conditionOption = optionFinder.findConditionOption(conditions);
		ConditionOption conditionOption1 = conditionOption.getConditionOption(memberId);

		//TODO: 참여여부

		List<QuestDto> questDtos = questRepository.findByConditionOption(conditionOption1).stream().map(QuestDto::of).toList();
		return PromotionDetails.of(promotion, false, questDtos);
	}

	@Override
	@Transactional
	public Page<PromotionSummary> findPromotions(Pageable pageable) {
		Page<Promotion> promotions = repository.findPromotions(pageable);
		return promotions.map(PromotionSummary::of);
	}

}
