package com.mosinsa.order.command.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoneyConverterTest {

    @Test
    void convertToDatabaseColumn() {
        MoneyConverter moneyConverter = new MoneyConverter();
        Money money = Money.of(1000);
        Integer intValue = moneyConverter.convertToDatabaseColumn(money);
        assertThat(intValue.intValue()).isEqualTo(1000);
    }

    @Test
    void convertNullToDatabaseColumn() {
        MoneyConverter moneyConverter = new MoneyConverter();

        Integer intValue = moneyConverter.convertToDatabaseColumn(null);
        assertThat(intValue).isNull();
    }

    @Test
    void convertToEntityAttribute() {
        MoneyConverter moneyConverter = new MoneyConverter();
        Money money = moneyConverter.convertToEntityAttribute(1000);
        assertThat(money).isEqualTo(Money.of(1000));
    }

    @Test
    void convertNullToEntityAttribute() {
        MoneyConverter moneyConverter = new MoneyConverter();
        Money money = moneyConverter.convertToEntityAttribute(null);
        assertThat(money).isNull();
    }

}