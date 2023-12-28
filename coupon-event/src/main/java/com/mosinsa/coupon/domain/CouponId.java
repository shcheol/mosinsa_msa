package com.mosinsa.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class CouponId implements Serializable {

    @Column(name = "coupon_id")
    private String id;

    public static CouponId newId() {
        CouponId id = new CouponId();
        id.id = UUID.randomUUID().toString();
        return id;
    }

    public static CouponId of(String id){
        if (!StringUtils.hasText(id)){
            throw new IllegalArgumentException();
        }
        CouponId couponId = new CouponId();
        couponId.id = id;
        return couponId;
    }

}
