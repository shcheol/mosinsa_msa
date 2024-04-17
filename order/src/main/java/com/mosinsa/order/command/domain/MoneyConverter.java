package com.mosinsa.order.command.domain;

import jakarta.persistence.AttributeConverter;

public class MoneyConverter implements AttributeConverter<Money, Integer> {
	@Override
	public Integer convertToDatabaseColumn(Money attribute) {
		return attribute == null ? null : attribute.getValue();
	}

	@Override
	public Money convertToEntityAttribute(Integer dbData) {
		return dbData == null ? null : Money.of(dbData);
	}
}
