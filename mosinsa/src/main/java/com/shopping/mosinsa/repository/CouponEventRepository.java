package com.shopping.mosinsa.repository;

import com.shopping.mosinsa.dto.CouponEventWrapper;
import com.shopping.mosinsa.entity.CouponEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CouponEventRepository extends JpaRepository<CouponEvent, Long> {

    @Query(value = "select new com.shopping.mosinsa.dto.CouponEventWrapper(e) from CouponEvent e where concat(e.eventName, e.id) like :eventKey")
    Optional<CouponEventWrapper> findEventKey(@Param("eventKey") String eventKey);

}
