package com.mosinsa.promotion.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
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
@EqualsAndHashCode
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
}
