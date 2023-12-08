package com.mosinsa.product.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

	private int value;

	public static Money of(int value) {
		inputValidCheck(value);

		return new Money(value);
	}

	private Money(int value){
		this.value = value;
	}

	public Money multiply(int multiplier) {
		return new Money(value * multiplier);
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
