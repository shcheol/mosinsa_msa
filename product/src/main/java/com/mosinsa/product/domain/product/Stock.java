package com.mosinsa.product.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

	@Id
	private String id;

	private long remain;

	public static Stock of(final long total) {
		inputValidCheck(total);

		Stock stock = new Stock();
		stock.id = UUID.randomUUID().toString();
		stock.remain = total;
		return stock;
	}

	public void increase(final long value) {
		inputValidCheck(value);

		this.remain = remain + value;
	}

	public void decrease(final long value) {
		inputValidCheck(value);

		long decreasedRemain = remain - value;
		remainValidCheck(decreasedRemain);

		this.remain = decreasedRemain;
	}

	private static void inputValidCheck(long value) {
		if (value < 1L) {
			throw new InvalidStockException("수량은 0보다 큰 값이 필요합니다.");
		}
	}

	private static void remainValidCheck(long value) {
		if (value < 0L) {
			throw new InvalidStockException("수량이 부족합니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Stock stock = (Stock) o;
		return Objects.equals(this.id, stock.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
