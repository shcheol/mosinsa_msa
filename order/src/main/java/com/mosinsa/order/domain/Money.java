package com.mosinsa.order.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    private int price;

    public static Money of(int value) {
        inputValidCheck(value);

        return new Money(value);
    }

    private Money(int value){
        this.price = value;
    }

    public Money multiply(int multiplier) {
        return new Money(price * multiplier);
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
        return price == money.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
