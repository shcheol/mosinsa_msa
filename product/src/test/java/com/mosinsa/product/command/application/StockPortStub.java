package com.mosinsa.product.command.application;

import com.mosinsa.product.infra.redis.StockOperand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockPortStub implements StockPort{

    private Map<String, Long> stockMap;

    public StockPortStub() {
        this.stockMap = new HashMap<>();
        stockMap.put("productId1", 0L);
        stockMap.put("productId2", 0L);
        stockMap.put("productId3", 30L);
        stockMap.put("productId4", 40L);
        stockMap.put("productId5", 50L);
    }

    @Override
    public long currentStock(String key) {
        return stockMap.getOrDefault(key, 0L);
    }

    @Override
    public void setStock(String key, long stock) {
        stockMap.put(key, stock);
    }

    @Override
    public StockResult tryDecrease(String customerId, String orderId, List<StockOperand> stockOperands) {

        return orderId.equals("fail")?StockResult.FAIL: StockResult.SUCCESS;
    }

    @Override
    public StockResult tryIncrease(String customerId, String orderId, List<StockOperand> stockOperands) {
        return orderId.equals("fail")?StockResult.FAIL: StockResult.SUCCESS;
    }

}
