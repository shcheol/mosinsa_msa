package com.mosinsa.couponissue.repository;



import com.mosinsa.couponissue.entity.Coupon;
import com.mosinsa.couponissue.entity.CouponEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByCouponEvent(CouponEvent couponEvent);
}
