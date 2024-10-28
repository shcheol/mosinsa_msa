package com.mosinsa.product.command.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Entity
public class StockHistory extends BaseIdEntity {

	private String orderNum;

	private String memberId;

	private String productId;

	private long quantity;

	@Enumerated(EnumType.STRING)
	private StockHistoryType type;

	protected StockHistory() {
	}

	public static StockHistory of(String orderNum, String memberId, String productId, long quantity, StockHistoryType type) {
		StockHistory stockHistory = new StockHistory();
		stockHistory.orderNum = orderNum;
		stockHistory.memberId = memberId;
		stockHistory.productId = productId;
		stockHistory.quantity = quantity;
		stockHistory.type = type;
		return stockHistory;
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
