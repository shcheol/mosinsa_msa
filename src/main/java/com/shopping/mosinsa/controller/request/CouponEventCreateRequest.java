package com.shopping.mosinsa.controller.request;

import com.shopping.mosinsa.entity.DiscountPolicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CouponEventCreateRequest {

    private String eventName;
    private DiscountPolicy discountPolicy;
    private int quantity;

    private LocalDateTime eventStartAt;
    private LocalDateTime expiryDate;

    public CouponEventCreateRequest(String eventName, DiscountPolicy discountPolicy, int quantity, LocalDateTime eventStartAt, LocalDateTime expiryDate) {
        Assert.notNull(eventName,"이벤트 이름은 필수입니다.");
        Assert.isTrue(quantity>0, "수량은 1개 이상이어야합니다.");
        Assert.isTrue(!discountPolicy.equals(DiscountPolicy.NONE), "할인이 없는 쿠폰은 생성하지 못합니다.");
//        Assert.isTrue(eventStartAt.isAfter(LocalDateTime.now()), "이벤트 시작은 현재 시간 이후로 생성할 수 있습니다.");
        Assert.isTrue(expiryDate.isAfter(eventStartAt), "이벤트 시작전에 만료되는 쿠폰은 생성할수 없습니다.");
        Assert.isTrue(expiryDate.isAfter(LocalDateTime.now()), "만료 기간이 이미지난 쿠폰은 생성할 수 없습니다.");

        this.eventName = eventName;
        this.discountPolicy = discountPolicy;
        this.quantity = quantity;
        this.eventStartAt = eventStartAt;
        this.expiryDate = expiryDate;
    }
}
