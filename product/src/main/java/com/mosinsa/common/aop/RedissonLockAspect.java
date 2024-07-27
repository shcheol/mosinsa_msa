package com.mosinsa.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class RedissonLockAspect {

    private final RedissonClient redissonClient;


    @Around("@annotation(redissonLock)")
    public Object tryLock(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) throws Throwable {

        RLock lock = redissonClient.getLock(redissonLock.value());
        try {
            if (!lock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.timeUnit())) {
                throw new TryLockFailException();
            }
            return joinPoint.proceed();
        } finally {
            try {
                lock.unlock();
            } catch (IllegalMonitorStateException e) {
                log.info("Redisson Lock Already UnLock {}", redissonLock.value());
            }
        }
    }
}
