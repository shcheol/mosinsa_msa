package com.mosinsa.promotion.command.domain;

import com.mosinsa.InMemoryJpaTest;
import com.mosinsa.code.EqualsAndHashcodeUtils;
import com.mosinsa.common.exception.CouponException;
import com.mosinsa.coupon.command.domain.DiscountPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CouponGroupInfoTest extends InMemoryJpaTest {

    @Autowired
    QuestRepository repository;

    @Test
    void equalsAndHashcode(){
        CouponGroupInfo couponGroupInfo1 = repository.findById(1L).get().getCouponGroupInfoList().stream().filter(cg -> cg.getId().equals(1L)).findAny().get();
        CouponGroupInfo couponGroupInfo2 = repository.findById(1L).get().getCouponGroupInfoList().stream().filter(cg -> cg.getId().equals(1L)).findAny().get();
        CouponGroupInfo protectedConstructor = new CouponGroupInfo();
        CouponGroupInfo other = repository.findById(2L).get().getCouponGroupInfoList().stream().findAny().get();

        boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(couponGroupInfo1, couponGroupInfo2, protectedConstructor, other);
        Assertions.assertThat(b).isTrue();

    }

    @Test
    void of(){
        Quest quest = repository.findById(1L).get();
        Assertions.assertThat(quest.getCouponGroupInfoList()).hasSize(1);
        CouponGroupInfo of = CouponGroupInfo.of(1, 10, DiscountPolicy.TEN_PERCENTAGE, quest);

        Assertions.assertThat(of.getCouponGroupSequence()).isEqualTo(1);
        Assertions.assertThat(of.getQuantity()).isEqualTo(10);
        Assertions.assertThat(of.getDiscountPolicy()).isEqualTo(DiscountPolicy.TEN_PERCENTAGE);
        Assertions.assertThat(of.getQuest()).isEqualTo(quest);

        Assertions.assertThat(quest.getCouponGroupInfoList()).hasSize(2);
    }

    @Test
    void issue(){
        Quest quest = repository.findById(1L).get();
        CouponGroupInfo of = CouponGroupInfo.of(1, 2, DiscountPolicy.TEN_PERCENTAGE, quest);
        Assertions.assertThat(of.getQuantity()).isEqualTo(2);
        of.issue();
        Assertions.assertThat(of.getQuantity()).isEqualTo(1);
        of.issue();
        Assertions.assertThat(of.getQuantity()).isZero();
        assertThrows(CouponException.class, () -> of.issue());

    }
}