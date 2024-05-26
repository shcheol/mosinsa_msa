package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ShippingInfoTest {

    @Test
    void of() {
        ShippingInfoDto shippingInfoDto = new ShippingInfoDto("myHome", new AddressDto("zipCode", "address1", "address2"), new ReceiverDto("myname", "010-xxx-xxxx"));
        ShippingInfo shippingInfoA = ShippingInfo.of(shippingInfoDto);
        ShippingInfo shippingInfoB = ShippingInfo.of(shippingInfoDto);
        assertThat(shippingInfoA).isNotNull();
        assertThat(shippingInfoA).isEqualTo(shippingInfoB).hasSameHashCodeAs(shippingInfoB);

    }

}