package com.mosinsa.product.domain.product;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesId implements Serializable {

    @Column(name = "likes_id")
    private String id;

    public static LikesId newId() {
        return new LikesId(UUID.randomUUID().toString());
    }


    public static LikesId of(String id) {
        return new LikesId(id);
    }

    private LikesId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikesId likesId = (LikesId) o;

        return Objects.equals(id, likesId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
