package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingInfoTest {

    @Test
    void of() {
        ShippingInfoDto shippingInfoDto = new ShippingInfoDto("myHome", new AddressDto("zipCode", "address1", "address2"), new ReceiverDto("myname", "010-xxx-xxxx"));
        ShippingInfo shippingInfoA = ShippingInfo.of(shippingInfoDto);
        ShippingInfo shippingInfoB = ShippingInfo.of(shippingInfoDto);
        assertThat(shippingInfoA).isNotNull().isEqualTo(shippingInfoB).hasSameHashCodeAs(shippingInfoB)
                .isNotEqualTo(null).isNotEqualTo(new TestClass());
        ShippingInfo shippingInfoC = ShippingInfo.of(new ShippingInfoDto("messgae", new AddressDto("zipCode", "address1", "address2"), new ReceiverDto("myname", "010-xxx-xxxx")));
        assertThat(shippingInfoA).isNotEqualTo(shippingInfoC).doesNotHaveSameHashCodeAs(shippingInfoC);
    }
    static class TestClass{

    }

}