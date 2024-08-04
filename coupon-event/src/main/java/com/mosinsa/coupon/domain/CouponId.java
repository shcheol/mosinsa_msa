package com.mosinsa.coupon.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Access(AccessType.FIELD)
public class CouponId implements Serializable {

    @Column(name = "coupon_id")
    private String id;

    public static CouponId newId() {
        CouponId id = new CouponId();
        id.id = UUID.randomUUID().toString();
        return id;
    }

    protected CouponId() {
    }

    public static CouponId of(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException();
        }
        CouponId couponId = new CouponId();
        couponId.id = id;
        return couponId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CouponId couponId)) return false;
        return Objects.equals(id, couponId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
