package com.mosinsa.coupon.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        if (o == null || getClass() != o.getClass()) return false;

        CouponId id = (CouponId) o;
        return Objects.equals(this.id, id.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
