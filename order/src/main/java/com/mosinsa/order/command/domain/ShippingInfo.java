package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingInfo {

    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code"))
    @AttributeOverride(name = "address1", column = @Column(name = "shipping_addr1"))
    @AttributeOverride(name = "address2", column = @Column(name = "shipping_addr2"))
    private Address address;

    @Column(name = "shipping_message")
    private String message;

    @Embedded
    private Receiver receiver;

    public static ShippingInfo of(ShippingInfoDto shippingInfoDto) {
        ShippingInfo shippingInfo = new ShippingInfo();
        shippingInfo.address = Address.of(shippingInfoDto.address());
        shippingInfo.message = shippingInfoDto.message();
        shippingInfo.receiver = Receiver.of(shippingInfoDto.receiver());
        return shippingInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShippingInfo that)) return false;
        return Objects.equals(address, that.address) && Objects.equals(message, that.message) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, message, receiver);
    }
}
