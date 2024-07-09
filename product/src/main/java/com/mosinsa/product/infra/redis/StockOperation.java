package com.mosinsa.product.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StockOperation {

	private final RedisTemplate<String, String> redisTemplate;

	public List<Long> decreaseAndGet(List<StockOperand> stocks) {

		return redisTemplate.execute(new SessionCallback<>() {
			@Override
			public <K, V> List<Long> execute(RedisOperations<K, V> operations) throws DataAccessException {

				operations.multi();
				for (StockOperand stock : stocks) {
					operations.opsForValue().decrement((K) stock.key(), stock.quantity());
				}
				return operations.exec().stream().mapToLong(Long.class::cast).boxed().toList();
			}
		});
	}

	public List<Long> increaseAndGet(List<StockOperand> stocks) {
		return redisTemplate.execute(new SessionCallback<>() {
			@Override
			public <K, V> List<Long> execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();

				for (StockOperand stock : stocks) {
					operations.opsForValue().increment((K) stock.key(), stock.quantity());
				}
				return operations.exec().stream().mapToLong(Long.class::cast).boxed().toList();
			}
		});
	}

	public long get(String key) {
		try {
			return Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get(key)));
		} catch (Exception ignored) {
			return 0;
		}
	}

	public void set(String key, long stock) {
		redisTemplate.opsForValue().set(key, String.valueOf(stock));
	}


}
