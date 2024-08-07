package com.mosinsa.coupon.command.application;

import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.application.CouponServiceImpl;
import com.mosinsa.coupon.command.domain.*;
import com.mosinsa.coupon.query.application.dto.CouponSearchCondition;
import com.mosinsa.coupon.infra.repository.CouponRepository;
import com.mosinsa.promotion.domain.PromotionCreatedEvent;
import com.mosinsa.promotion.domain.PromotionId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class CouponServiceImplTest {

    @Autowired
    CouponServiceImpl service;

    @Autowired
    CouponRepository repository;

    @Test
    @DisplayName("쿠폰발급")
    void issue() {
        CouponId couponId = service.issue(new CouponIssuedEvent("1", "promotion2"));

        Coupon coupon = repository.findById(couponId).get();
        assertThat(coupon.getIssuedDate()).isNotNull();
        assertThat(coupon.getMemberId()).isEqualTo("1");
        assertThat(coupon.getState()).isEqualTo(CouponState.ISSUED);
    }

    @Test
    @DisplayName("쿠폰발급-동시요청")
    void issueConcurrency() throws InterruptedException {

        assertThat(
                repository.findCouponsInPromotion(
                        new CouponSearchCondition(null, "promotion4"))
        ).hasSize(10);

        ExecutorService es = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            es.execute(() -> {
                service.issue(new CouponIssuedEvent(UUID.randomUUID().toString(), "promotion4"));
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("실행 시간: " + (end - start));

        es.shutdown();
        assertThat(
                repository.findCouponsInPromotion(
                        new CouponSearchCondition(null, "promotion4"))
        ).isEmpty();
    }

    @Test
    @DisplayName("쿠폰발급 실패 수량부족")
    void issueCouponFail1() {
        service.issue(new CouponIssuedEvent("1", "promotion2"));
        service.issue(new CouponIssuedEvent("2", "promotion2"));
        CouponIssuedEvent failEvent = new CouponIssuedEvent("3", "promotion2");
        assertThrows(CouponException.class,
                () -> service.issue(failEvent));
    }

    @Test
    @DisplayName("쿠폰발급 실패 중복참여")
    void issueCouponFail2() {
        service.issue(new CouponIssuedEvent("1", "promotion2"));
        CouponIssuedEvent failEvent = new CouponIssuedEvent("1", "promotion2");
        assertThrows(DataIntegrityViolationException.class,
                () -> service.issue(failEvent));
    }

    @Test
    @DisplayName("insert using JPA saveAll")
    void createAll() {
        PromotionId promotionId = PromotionId.of("promotionTest1");
        service.createAll(new PromotionCreatedEvent(promotionId, 100,
                CouponDetails.of(LocalDateTime.of(2024, 10, 28, 00, 00), DiscountPolicy.TEN_PERCENTAGE)));
        assertThat(repository.findCouponsInPromotion(
                new CouponSearchCondition(null, promotionId.getId())))
                .hasSize(100);
    }

    @Test
    @DisplayName("batch insert using jdbcTemplate")
    void createAllByBulkInsert() {
        PromotionId promotionId = PromotionId.of("promotionTest2");
        service.createAllByBatchInsert(new PromotionCreatedEvent(promotionId, 100,
                CouponDetails.of(LocalDateTime.of(2024, 10, 28, 00, 00), DiscountPolicy.TEN_PERCENTAGE)));
        assertThat(repository.findCouponsInPromotion(
                new CouponSearchCondition(null, promotionId.getId())))
                .hasSize(100);
    }

//    @Test
//    void createNewMember() {
//
//        String memberId = "memberId";
//        service.createForNewMember(memberId);
//        List<CouponDto> couponDtos = service.get(memberId);
//        assertThat(couponDtos).isNotEmpty();
//    }

    @Test
    void createNewMember_중복요청() {

        String memberId = "1";
        assertThrows(CouponException.class, () -> service.createForNewMember(memberId));
    }
}