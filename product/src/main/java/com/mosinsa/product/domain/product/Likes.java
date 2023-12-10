package com.mosinsa.product.domain.product;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    private String memberId;

    public static Likes of(String memberId) {
        Likes likes = new Likes();
        likes.memberId = memberId;
        return likes;
    }
}
