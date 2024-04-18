package com.mosinsa.product.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(String key){
        log.info("lock {}", key);
        return redisTemplate.opsForValue()
                .setIfAbsent(key, "lock", Duration.ofMillis(3000));
    }

    public void unlock(String key){
        log.info("unlock {}", key);
        redisTemplate.delete(key);
    }

	public static void main(String[] args) {
		ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();


		Future<Object> objectFuture = new Future<>() {
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return false;
			}

			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public boolean isDone() {
				return false;
			}

			@Override
			public Object get() throws InterruptedException, ExecutionException {
				return null;
			}

			@Override
			public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				return null;
			}
		};

	}
}
