package com.mosinsa.common.aop;

import com.mosinsa.common.exception.CouponException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@Order(1)
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retryMethod(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        int times = retry.times();

        for (int i = 1; i <= times; i++) {
            try {
                return joinPoint.proceed();
            } catch (CouponException couponException) {
                throw new CouponException(couponException.getMessage(), couponException.getError());
            } catch (Exception e) {
                log.error("try {} fail. auto retry max {}", i, times, e);
            }
        }

        throw new RetryFailException();
    }
}
