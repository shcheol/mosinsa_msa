package com.mosinsa.common.aop;

import com.mosinsa.code.LockTestClass;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class LockConfig {

    @Bean
    public LockTestClass lockTestClass(){
        return new LockTestClass();
    }
}
