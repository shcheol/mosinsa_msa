package com.mosinsa.product.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseIdEntity {

	private long total;

	@Enumerated(EnumType.STRING)
	private StockStatus status;

	public static Stock of(final long total) {
		inputValidCheck(total);

		Stock stock = new Stock();
		stock.total = total;
		stock.status = total > 0L ? StockStatus.ON : StockStatus.OFF;
		return stock;
	}

	public void updateAvailable() {
		this.status = StockStatus.ON;
	}

	public void updateSoldOut() {
		this.status = StockStatus.SOLD_OUT;
	}

	private static void inputValidCheck(long value) {
		if (value < 0L) {
			throw new InvalidStockException("수량은 0이상이어야 합니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
