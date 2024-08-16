package com.mosinsa.promotion.query.dto;

import com.mosinsa.promotion.command.domain.Quest;

public record QuestDto(Long id) {

	public static QuestDto of(Quest quest){
		return new QuestDto(quest.getId()) ;
	}
}
