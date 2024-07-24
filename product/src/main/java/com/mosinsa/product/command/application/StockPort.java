package com.mosinsa.product.command.application;

import com.mosinsa.product.infra.redis.StockOperand;

import java.util.List;

public interface StockPort {

	long currentStock(String key);

	void setStock(String key, long stock);

	StockResult tryDecrease(String customerId, String orderId, List<StockOperand> stockOperands);

	StockResult tryIncrease(String customerId, String orderId, List<StockOperand> stockOperands);
}
