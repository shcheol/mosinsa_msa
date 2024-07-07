package com.mosinsa.product.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
public class StockHistory {

	@Id
	@Column(name = "stock_history")
	private String id;

	private String orderNum;

	private String memberId;

	private String productId;

	private Long quantity;

	private StockHistoryType type;

	protected StockHistory() {
	}

	public static StockHistory of(String orderNum, String memberId, String productId, Long quantity, StockHistoryType type) {
		StockHistory stockHistory = new StockHistory();
		stockHistory.id = UUID.randomUUID().toString();
		stockHistory.orderNum = orderNum;
		stockHistory.memberId = memberId;
		stockHistory.productId = productId;
		stockHistory.quantity = quantity;
		stockHistory.type = type;
		return stockHistory;
	}
}
