package com.shopping.mosinsa.service;

import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.controller.request.CouponIssuanceRequest;
import com.shopping.mosinsa.entity.Coupon;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.entity.Customer;
import com.shopping.mosinsa.pub.CouponEventPublisher;
import com.shopping.mosinsa.repository.CouponEventRepository;
import com.shopping.mosinsa.repository.CouponFactoryRepository;
import com.shopping.mosinsa.repository.CouponRepository;
import com.shopping.mosinsa.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponFactoryRepository couponFactoryRepository;
    private final CouponEventRepository couponEventRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final CouponEventPublisher couponEventPublisher;

    private final EntityManager em;


    @Transactional
    public CouponEvent createCouponEvent(CouponEventCreateRequest request) {

        CouponEvent couponEvent = CouponEvent.createCouponEvent(request);
        couponEventRepository.save(couponEvent);

        couponFactoryRepository.bulkInsert(Coupon.createCoupon(couponEvent, request.getDiscountPolicy(), request.getExpiryDate()), request.getQuantity());
        couponEvent.getCoupons().addAll(couponRepository.findAllByCouponEvent(couponEvent));

        em.flush();
        em.clear();

        couponEventPublisher.publishCouponEvent(couponEvent);

        return couponEvent;
    }

    @Transactional
    public Coupon couponIssuanceRequest(CouponIssuanceRequest request){
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("고객정보가없습니다."));
        CouponEvent couponEvent = couponEventRepository.findById(request.getCouponEventId()).orElseThrow(() -> new IllegalArgumentException("쿠폰이벤트가 없습니다."));

        Assert.isTrue(couponEvent.getEventStartAt().isBefore(LocalDateTime.now()), "이벤트가 시작하지 않았습니다.");
        Assert.isTrue(couponEvent.getCoupons().size()>0, "남은 수량이 없습니다.");

		return couponEvent.issuanceCoupon(customer);
    }

    public CouponEvent findCouponEvent(Long id) {
        return couponEventRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("쿠폰이벤트가 없습니다."));
    }
}
