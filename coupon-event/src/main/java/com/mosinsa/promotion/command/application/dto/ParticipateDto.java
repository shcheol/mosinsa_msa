package com.mosinsa.promotion.command.application.dto;

import com.mosinsa.promotion.query.dto.QuestDto;

import java.util.List;

public record ParticipateDto(String promotionId, String memberId, List<QuestDto> questDtos) {
}
