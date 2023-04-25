package com.shopping.mosinsa.repository;

import com.shopping.mosinsa.dto.CouponEventWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponEventRepositoryTest {


    @Autowired
    CouponEventRepository couponEventRepository;

    @Test
    void test(){
        CouponEventWrapper eventKey = couponEventRepository.findEventKey("10주년이벤트1").get();

        System.out.println(eventKey.toString());
    }

}