package com.mosinsa.product.common.aop;

import com.mosinsa.product.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Aspect
@Component
@RequiredArgsConstructor
public class NamedLockAspect {

	private final ProductRepository repository;


	@Around("@annotation(namedLock)")
	public Object tryLock(ProceedingJoinPoint joinPoint, NamedLock namedLock) {
		String key = namedLock.value();

		try {
			repository.getLock(key, namedLock.timeout());
			return joinPoint.proceed();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			repository.unlock(key);
		}
	}
}
