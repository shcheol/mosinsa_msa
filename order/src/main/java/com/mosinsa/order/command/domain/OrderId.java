package com.mosinsa.order.command.domain;

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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class OrderId implements Serializable {

	@Column(name = "order_id")
    private String id;

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID().toString());
    }

    public static OrderId of(String id) {
        if (!StringUtils.hasText(id)){
            throw new IllegalArgumentException();
        }
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
