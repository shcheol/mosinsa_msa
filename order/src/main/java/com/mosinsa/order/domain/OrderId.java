package com.mosinsa.order.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class OrderId implements Serializable {

    @EmbeddedId
    private String id;

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID().toString());
    }


    public static OrderId of(String id) {
        return new OrderId(id);
    }

    private OrderId(String id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderId orderId = (OrderId) o;

        return Objects.equals(id, orderId.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
