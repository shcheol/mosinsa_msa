package com.shopping.mosinsa.coupon;

import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.entity.*;
import com.shopping.mosinsa.repository.CouponEventRepository;
import com.shopping.mosinsa.repository.CouponFactoryRepository;
import com.shopping.mosinsa.repository.CouponRepository;
import com.shopping.mosinsa.repository.CustomerRepository;
import com.shopping.mosinsa.service.CouponService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.shopping.mosinsa.coupon.CouponSteps.쿠폰발급요청_생성;
import static com.shopping.mosinsa.coupon.CouponSteps.쿠폰이벤트생성_요청;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class CouponServiceTest {

    @Autowired
    CouponFactoryRepository couponFactoryRepository;

    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponService couponService;

    @Autowired
    CouponEventRepository couponEventRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void 쿠폰이벤트생성() throws Exception{

        //given
        int stock = 50;
        CouponEventCreateRequest request = 쿠폰이벤트생성_요청(stock);

        //when
        CouponEvent couponEvent = couponService.createCouponEvent(request);

        //then
        assertThat(couponEvent.getCoupons().size()).isEqualTo(stock);

    }

    @Test
    public void 쿠폰발급요청() {

        Customer customer = new Customer(CustomerGrade.BRONZE);
        customerRepository.save(customer);

        int stock = 50;
        CouponEventCreateRequest request = 쿠폰이벤트생성_요청(stock);

        CouponEvent couponEvent = couponService.createCouponEvent(request);

        CouponIssuanceRequest couponCustomerRequest = 쿠폰발급요청_생성(couponEvent.getEventName(), couponEvent.getId(), customer.getId());
        Coupon coupon = couponService.couponIssuanceRequest(couponCustomerRequest);

        Assertions.assertThat(coupon.getCustomer().getId()).isEqualTo(customer.getId());
//        Assertions.assertThat(couponEvent.getCoupons().size()).isEqualTo(49);
        Assertions.assertThat(couponEvent.getQuantity()).isEqualTo(49);

    }


}