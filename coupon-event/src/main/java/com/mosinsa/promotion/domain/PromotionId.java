package com.mosinsa.promotion.domain;

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
@Access(AccessType.FIELD)
@Getter
public class PromotionId implements Serializable {

    @Column(name = "promotion_id")
    private String id;

    public static PromotionId newId() {
        PromotionId id = new PromotionId();
        id.id = UUID.randomUUID().toString();
        return id;
    }

	public static PromotionId of(String id) {
        if (!StringUtils.hasText(id)){
            throw new IllegalArgumentException();
        }
		PromotionId promotionId = new PromotionId();
        promotionId.id = id;
		return promotionId;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromotionId id = (PromotionId) o;
        return Objects.equals(this.id, id.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
