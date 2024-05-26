package com.mosinsa.order.command.domain;

import java.util.Objects;

public class Money {

    private final int value;

	public int getValue(){
		return value;
	}
    public static Money of(int value) {
        inputValidCheck(value);
        return new Money(value);
    }

    private Money(int value){
        this.value = value;
    }

    public Money multiply(int multiplier) {
		inputValidCheck(multiplier);
        return new Money(value * multiplier);
    }

    public Money minus(int minus) {
        inputValidCheck(minus);
        int resultAmount = value - minus;
        if(resultAmount < 0){
            throw new InvalidMoneyException();
        }
        return new Money(resultAmount);
    }

    private static void inputValidCheck(int value) {
        if(value < 1){
            throw new InvalidMoneyException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
