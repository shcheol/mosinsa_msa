package com.mosinsa.product.command.application;

import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.StockHistory;
import com.mosinsa.product.command.domain.StockHistoryType;
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StockService {

	private final StockHistoryRepository historyRepository;
	private final RedisTemplate<String, String> redisTemplate;

	public long currentStock(String productId) {

		return Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get(productId)));
	}

	@Transactional
	public void decreaseStocks(String customerId, String orderId, List<OrderProductRequest> requests) {

		// 상품목록 레디스로 현재 수량 감소
		for (OrderProductRequest request : requests) {
			Long decrement = redisTemplate.opsForValue().decrement(request.productId(), request.quantity());
		}

		redisTemplate.execute(new SessionCallback<Object>() {

			@Override
			public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();

				operations.exec();
				return null;
			}
		});

		// 성공
		// 0인 상품은 stock 상태 sold_out

		List<StockHistory> stockHistories = requests.stream()
				.map(op -> StockHistory.of(orderId, customerId, op.productId(), op.quantity(), StockHistoryType.MINUS))
				.toList();
		historyRepository.saveAllAndFlush(stockHistories);
	}

	@Transactional
	public void increaseStocks(String customerId, String orderId, List<CancelOrderProductRequest> requests) {

		List<StockHistory> stockHistories = requests.stream()
				.map(op -> StockHistory.of(orderId, customerId, op.productId(), op.quantity(), StockHistoryType.PLUS))
				.toList();
		historyRepository.saveAll(stockHistories);
	}

	public void setStock(String key, long stock) {
		redisTemplate.opsForValue().set(key, String.valueOf(stock));
	}
}
