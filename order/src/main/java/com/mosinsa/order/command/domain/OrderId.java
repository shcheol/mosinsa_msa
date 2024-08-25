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
@Access(AccessType.FIELD)
public class OrderId implements Serializable {

	@Column(name = "order_id")
    private String id;

    public static OrderId newId() {
        OrderId id = new OrderId();
        id.id = UUID.randomUUID().toString();
        return id;
    }

    public static OrderId of(String id) {
        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException();
        }
        OrderId orderId = new OrderId();
        orderId.id = id;
        return orderId;
    }

    protected OrderId(){
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
