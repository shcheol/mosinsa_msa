package com.mosinsa.common.aop;

import com.mosinsa.common.exception.CouponError;
import com.mosinsa.common.exception.CouponException;
import org.springframework.stereotype.Component;

@Component
public class TestClient {

    private int successCount=0;
    private int failCount=0;
    private int failCount2=0;

    @Retry(times = 3)
    void successMethod(){
        System.out.println("TestClass.successMethod");
        successCount++;
    }

    @Retry(times = 3)
    void failMethod(){
        System.out.println("TestClass.failMethod");
        failCount++;
        throw new RuntimeException();
    }

	@Retry(times = 3)
	void failMethod2(){
		System.out.println("TestClass.failMethod");
		failCount2++;
		throw new CouponException(CouponError.INTERNAL_SERVER_ERROR);
	}

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailCount() {
        return failCount;
    }
    public int getFailCount2() {
        return failCount2;
    }
}
