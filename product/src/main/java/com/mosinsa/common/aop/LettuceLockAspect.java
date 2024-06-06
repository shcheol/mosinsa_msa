package com.mosinsa.common.aop;

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
	public Object tryLock(ProceedingJoinPoint joinPoint, LettuceLock lettuceLock) throws Throwable {
		String key = lettuceLock.value();
		try {
			while (Boolean.FALSE.equals(lockRepository.lock((key)))) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new InterruptedException();
				}
			}
			return joinPoint.proceed();
		} finally {
			lockRepository.unlock(key);
		}
	}
}
