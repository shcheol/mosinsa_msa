package com.mosinsa.product.common.aop;

import com.mosinsa.product.infra.redis.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LettuceLockAspect {

    private final RedisLockRepository lockRepository;


    @Around("@annotation(lettuceLock)")
    public Object tryLock(ProceedingJoinPoint joinPoint, LettuceLock lettuceLock){
        String key = lettuceLock.value();

        while (!lockRepository.lock((key))){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            lockRepository.unlock(key);
        }
    }
}
