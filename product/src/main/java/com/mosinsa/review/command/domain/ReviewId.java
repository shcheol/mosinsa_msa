package com.mosinsa.review.command.domain;

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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Embeddable
public class ReviewId implements Serializable {

    @Column(name = "review_id")
    private String id;
    public static ReviewId newId() {
        return new ReviewId(UUID.randomUUID().toString());
    }


    public static ReviewId of(String id) {
        return new ReviewId(id);
    }

    private ReviewId(String id){
        if (!StringUtils.hasText(id)){
            throw new IllegalArgumentException("invalid Id value");
        }
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewId reviewId = (ReviewId) o;

        return Objects.equals(id, reviewId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
