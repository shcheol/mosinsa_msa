package com.mosinsa.promotion.command.application;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.promotion.command.application.dto.ParticipateDto;
import com.mosinsa.promotion.command.domain.*;
import com.mosinsa.promotion.infra.jpa.QuestRepository;
import com.mosinsa.promotion.infra.kafka.ParticipatedEvent;
import com.mosinsa.promotion.infra.kafka.ProduceTemplate;
import com.mosinsa.promotion.query.MemberParticipatedChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final ProduceTemplate produceTemplate;
    @Value("${mosinsa.topic.promotion.participate}")
    private String promotionParticipateTopic;

    @Override
    @Transactional
    public void participatePromotion(ParticipateDto participateDto) {

        DateUnit dateUnit = repository.findById(PromotionId.of(participateDto.promotionId()))
                .orElseThrow(() -> new CouponException(CouponError.NOT_FOUND))
                .getDateUnit();
        List<Quest> quests = participateDto.questDtos().stream()
                .map(questDto -> questRepository.findById(questDto.id()).orElseThrow())
                .toList();
        if (memberParticipatedChecker.isMemberParticipated(participateDto.memberId(), quests, dateUnit)) {
            throw new CouponException(CouponError.DUPLICATE_PARTICIPATION);
        }

        quests.stream().map(quest -> {
                    historyRepository.save(PromotionHistory.of(participateDto.memberId(), quest));
                    return quest.getCouponGroupInfoList();
                })
                .flatMap(List::stream)
                .forEach(couponGroupInfo -> {
                    couponGroupInfo.issue();
                    produceTemplate.produce(promotionParticipateTopic,
                            new ParticipatedEvent(participateDto.memberId(), couponGroupInfo.getCouponGroupSequence()));
                });
    }

}
