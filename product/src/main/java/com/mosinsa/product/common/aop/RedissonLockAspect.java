package com.mosinsa.product.common.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class RedissonLockAspect {

    private final RedissonClient redissonClient;


    @Around("@annotation(redissonLock)")
    public Object tryLock(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) {
        String key = redissonLock.value();

        RLock lock = redissonClient.getLock(key);
        try {
            if (!lock.tryLock(3, 5, TimeUnit.SECONDS)) {
                throw new TryLockFailException();
            }
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
