package com.mosinsa.product.command.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class StockHistory {

	@Id
	@Column(name = "stock_history_id")
	private String id;

	private String orderNum;

	private String memberId;

	private String productId;

	private long quantity;

	@Enumerated(EnumType.STRING)
	private StockHistoryType type;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdDate;

	protected StockHistory() {
	}

	public static StockHistory of(String orderNum, String memberId, String productId, long quantity, StockHistoryType type) {
		StockHistory stockHistory = new StockHistory();
		stockHistory.id = UUID.randomUUID().toString();
		stockHistory.orderNum = orderNum;
		stockHistory.memberId = memberId;
		stockHistory.productId = productId;
		stockHistory.quantity = quantity;
		stockHistory.type = type;
		return stockHistory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StockHistory that = (StockHistory) o;

		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
