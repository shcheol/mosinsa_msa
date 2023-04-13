package com.shopping.mosinsa.coupon;

import com.shopping.mosinsa.controller.request.CouponEventCreateRequest;
import com.shopping.mosinsa.entity.Coupon;
import com.shopping.mosinsa.entity.CouponEvent;
import com.shopping.mosinsa.entity.DiscountPolicy;
import com.shopping.mosinsa.repository.CouponEventRepository;
import com.shopping.mosinsa.repository.CouponFactoryRepository;
import com.shopping.mosinsa.repository.CouponRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.shopping.mosinsa.coupon.CouponSteps.쿠폰이벤트생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    CouponFactoryRepository couponFactoryRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponEventRepository couponEventRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName(value = "jdbc bulk insert 시간 측정")
    public void jdbcBulkInsert(){
        int stock = 50;
        CouponEventCreateRequest request = 쿠폰이벤트생성_요청(stock);

        CouponEvent couponEvent = CouponEvent.createCouponEvent(request);
        couponEventRepository.save(couponEvent);
//        em.flush();
//        em.clear();

        int result = couponFactoryRepository.bulkInsert(Coupon.createCoupon(couponEvent,DiscountPolicy.TEN_PERCENTAGE, LocalDateTime.of(2024, 12, 31, 23, 00)), stock);

        long count = couponRepository.findAllByCouponEvent(couponEvent).size();

        couponEvent.getCoupons().addAll(couponRepository.findAllByCouponEvent(couponEvent));

        assertThat(count).isEqualTo(stock);
        assertThat(result).isEqualTo(stock);
        assertThat(couponEvent.getCoupons().size()).isEqualTo(stock);
    }

    @Test
    @DisplayName(value = "jpa bulk insert 시간 측정")
    public void jpaBulkInsert(){
        int stock = 50;
        CouponEventCreateRequest request = 쿠폰이벤트생성_요청(stock);

        CouponEvent couponEvent = CouponEvent.createCouponEvent(request);
        couponEventRepository.save(couponEvent);

        ArrayList<Coupon> coupons = new ArrayList<>();

        for (int i=0;i<stock;i++){
            coupons.add(Coupon.createCoupon(couponEvent, DiscountPolicy.TEN_PERCENTAGE, LocalDateTime.of(2024,12,31,23,00)));
        }

        couponRepository.saveAll(coupons);

        long count = couponRepository.findAllByCouponEvent(couponEvent).size();

        assertThat(count).isEqualTo(stock);
    }
}