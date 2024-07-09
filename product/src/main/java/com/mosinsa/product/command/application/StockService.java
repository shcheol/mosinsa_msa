package com.mosinsa.product.command.application;

import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.StockHistory;
import com.mosinsa.product.command.domain.StockHistoryType;
import com.mosinsa.product.infra.redis.StockOperand;
import com.mosinsa.product.infra.redis.StockOperation;
import com.mosinsa.product.infra.repository.StockHistoryRepository;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
	public void decreaseStocks(String customerId, String orderId, List<StockOperand> stocks) {

		// 상품목록 레디스로 현재 수량 감소
//		for (OrderProductRequest orderProduct : orderProducts) {
//			Long decrement = redisTemplate.opsForValue().decrement(orderProduct.productId(), orderProduct.quantity());
//		}

		List<Long> execute = operation.decreaseAndGet(stocks);
		Optional<Long> any = execute.stream().filter(f -> f < 0).findAny();
		if (any.isEmpty()) {
			//정상
			// 성공
			// 0인 상품은 stock 상태 sold_out

			List<StockHistory> stockHistories = stocks.stream()
					.map(op -> StockHistory.of(orderId, customerId, op.key(), op.quantity(), StockHistoryType.MINUS))
					.toList();
			historyRepository.saveAll(stockHistories);

			checkSoldOut(stocks);

		} else {

			operation.increaseAndGet(stocks);
			throw new RuntimeException();
		}
	}



	private void checkSoldOut(List<StockOperand> stocks) {
//		orderProducts.stream().forEach();
	}

	@Transactional
	public void increaseStocks(String customerId, String orderId, List<StockOperand> stocks) {

		List<StockHistory> stockHistories = stocks.stream()
				.map(op -> StockHistory.of(orderId, customerId, op.key(), op.quantity(), StockHistoryType.PLUS))
				.toList();
		historyRepository.saveAll(stockHistories);
	}


}
