package com.mosinsa.coupon.infra.jpa;

import com.mosinsa.coupon.command.domain.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CustomCouponRepositoryImplTest {

    @Autowired
	CouponRepository repository;

//    @Test
    void conditionNullTest(){


    }

}