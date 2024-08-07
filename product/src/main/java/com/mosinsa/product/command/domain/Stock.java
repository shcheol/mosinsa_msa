package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
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
	@Column(name = "stock_id")
	private String id;

	private long total;

	@Enumerated(EnumType.STRING)
	private StockStatus status;

	public static Stock of(final long total) {
		inputValidCheck(total);

		Stock stock = new Stock();
		stock.id = UUID.randomUUID().toString();
		stock.total = total;
		stock.status = StockStatus.ON;
		return stock;
	}

	public void updateAvailable(){
		this.status = StockStatus.ON;
	}

	public void updateSoldOut(){
		this.status = StockStatus.SOLD_OUT;
	}

	private static void inputValidCheck(long value) {
		if (value < 1L) {
			throw new IllegalArgumentException("수량은 0보다 큰 값이 필요합니다.");
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
