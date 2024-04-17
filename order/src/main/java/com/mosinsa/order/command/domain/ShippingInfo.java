package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class ShippingInfo {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code")),
            @AttributeOverride(name = "address1", column = @Column(name = "shipping_addr1")),
            @AttributeOverride(name = "address2", column = @Column(name = "shipping_addr2"))
    })
    private Address address;

    @Column(name = "shipping_message")
    private String message;

    @Embedded
    private Receiver receiver;

    public static ShippingInfo of(ShippingInfoDto shippingInfoDto){
        ShippingInfo shippingInfo = new ShippingInfo();
        shippingInfo.address = Address.of(shippingInfoDto.address());
        shippingInfo.message = shippingInfoDto.message();
        shippingInfo.receiver = Receiver.of(shippingInfoDto.receiver());
        return shippingInfo;
    }

}
