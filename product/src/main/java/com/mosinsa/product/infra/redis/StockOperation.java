package com.mosinsa.product.infra.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StockOperation {

	private final RedisTemplate<String, String> redisTemplate;

	public List<Long> decreaseAndGet(List<StockOperand> stocks) {
		if(stocks.isEmpty()){
			return List.of();
		}
		return redisTemplate.execute(new SessionCallback<>() {
			@Override
			public <K, V> List<Long> execute(@NonNull RedisOperations<K, V> operations) throws DataAccessException {

				try {
					operations.multi();
					stocks.forEach(stock -> operations.opsForValue().decrement((K) stock.key(), stock.quantity()));
					return operations.exec().stream().mapToLong(Long.class::cast).boxed().toList();
				} catch (Exception e) {
					operations.discard();
					throw e;
				}
			}
		});
	}

	public List<Long> increaseAndGet(List<StockOperand> stocks) {
		if(stocks.isEmpty()){
			return List.of();
		}
		return redisTemplate.execute(new SessionCallback<>() {
			@Override
			public <K, V> List<Long> execute(@NonNull RedisOperations<K, V> operations) throws DataAccessException {
				try {
					operations.multi();
					stocks.forEach(stock -> operations.opsForValue().increment((K) stock.key(), stock.quantity()));
					return operations.exec().stream().mapToLong(Long.class::cast).boxed().toList();
				} catch (Exception e) {
					operations.discard();
					throw e;
				}
			}
		});
	}

	public long get(String key) {
		return get(key, 0);
	}

	public long get(String key, long defaultValue) {
		try {
			return Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get(key)));
		} catch (Exception ignored) {
			return defaultValue;
		}
	}

	public void set(String key, long stock) {
		redisTemplate.opsForValue().set(key, String.valueOf(stock));
	}


}
