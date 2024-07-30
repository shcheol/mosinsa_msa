package com.mosinsa.promotion.ui;

import com.mosinsa.promotion.query.dto.QuestDto;

import java.util.List;

public record ParticipateRequest(List<QuestDto> quests) {
}
