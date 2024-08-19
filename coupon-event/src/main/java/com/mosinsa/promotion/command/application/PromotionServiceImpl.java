package com.mosinsa.promotion.command.application;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.command.application.dto.ParticipateDto;
import com.mosinsa.promotion.command.domain.*;
import com.mosinsa.promotion.infra.kafka.KafkaEvents;
import com.mosinsa.promotion.infra.kafka.ParticipatedEvent;
import com.mosinsa.promotion.infra.repository.PromotionHistoryRepository;
import com.mosinsa.promotion.infra.repository.PromotionRepository;
import com.mosinsa.promotion.infra.repository.QuestRepository;
import com.mosinsa.promotion.query.MemberParticipatedChecker;
import com.mosinsa.promotion.query.dto.QuestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

	private final PromotionRepository repository;
	private final PromotionHistoryRepository historyRepository;
	private final QuestRepository questRepository;

	private final MemberParticipatedChecker memberParticipatedChecker;
	@Value("${mosinsa.topic.promotion.participate}")
	private String promotionParticipateTopic;

	@Override
	@Transactional
	public void participatePromotion(ParticipateDto participateDto) {

		PromotionId id = PromotionId.of(participateDto.promotionId());
		Promotion promotion = repository.findById(id)
				.orElseThrow(() -> new CouponException(CouponError.NOT_FOUND));

		// 참여여부
		DateUnit dateUnit = promotion.getDateUnit();

		//프로모션 조건으로 쿠폰 그룹 구하기
		List<QuestDto> questDtos = participateDto.questDtos();
		List<CouponGroupInfo> couponGroupInfos = questDtos.stream().map(questDto -> {
			// 프로모션 참여 이력 저장
			Quest quest = questRepository.findById(questDto.id()).orElseThrow();
			boolean memberParticipated = memberParticipatedChecker.isMemberParticipated(participateDto.memberId(), quest, dateUnit);
			if (memberParticipated) {
				throw new CouponException(CouponError.DUPLICATE_PARTICIPATION);
			}
			historyRepository.save(PromotionHistory.of(participateDto.memberId(), quest));
			return quest.getCouponGroupInfoList();
		}).flatMap(List::stream).toList();

		// 그룹 시퀀스에 따라 쿠폰 발행
		couponGroupInfos.forEach(couponGroupInfo -> {
			couponGroupInfo.issue();
			KafkaEvents.raise(
					promotionParticipateTopic,
					new ParticipatedEvent(participateDto.memberId(), couponGroupInfo.getCouponGroupSequence()));
		});

		//응답
	}

}
