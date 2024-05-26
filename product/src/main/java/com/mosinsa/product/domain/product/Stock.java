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
			throw new InvalidStockException();
		}
	}

	private static void remainValidCheck(long value) {
		if (value < 0L) {
			throw new InvalidStockException();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Stock id = (Stock) o;
		return Objects.equals(this.id, id.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
