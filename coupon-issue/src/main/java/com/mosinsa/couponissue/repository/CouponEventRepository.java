package com.mosinsa.couponissue.repository;

import com.mosinsa.couponissue.dto.CouponEventWrapper;
import com.mosinsa.couponissue.entity.CouponEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CouponEventRepository extends JpaRepository<CouponEvent, Long> {

    @Query(value = "select new com.mosinsa.couponissue.dto.CouponEventWrapper(e) from CouponEvent e where concat(e.eventName, e.id) like :eventKey")
    Optional<CouponEventWrapper> findEventKey(@Param("eventKey") String eventKey);

}
