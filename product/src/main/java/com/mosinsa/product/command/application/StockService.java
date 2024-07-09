package com.mosinsa.product.command.application;

import com.mosinsa.product.command.domain.StockHistory;
import com.mosinsa.product.command.domain.StockHistoryType;
import com.mosinsa.product.infra.redis.StockOperand;
import com.mosinsa.product.infra.redis.StockOperation;
import com.mosinsa.product.infra.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockService {

    private final StockHistoryRepository historyRepository;
    private final StockOperation operation;

    public long currentStock(String productId) {
        return operation.get(productId);
    }

    public void setStock(String key, long stock) {
        operation.set(key, stock);
    }

    @Transactional
    public StockResult tryDecrease(String customerId, String orderId, List<StockOperand> stockOperands) {

        List<Long> execute = operation.decreaseAndGet(stockOperands);
        if (!execute.isEmpty() && execute.stream().allMatch(f -> f >= 0)) {
            historyRepository.saveAll(stockOperands.stream()
                    .map(op -> StockHistory.of(orderId, customerId, op.key(), op.quantity(), StockHistoryType.MINUS))
                    .toList());

            return StockResult.SUCCESS;
        }
        operation.increaseAndGet(stockOperands);
        return StockResult.FAIL;
    }

    @Transactional
    public void tryIncrease(String customerId, String orderId, List<StockOperand> stocks) {

        List<StockHistory> stockHistories = stocks.stream()
                .map(op -> StockHistory.of(orderId, customerId, op.key(), op.quantity(), StockHistoryType.PLUS))
                .toList();
        historyRepository.saveAll(stockHistories);
    }


}
