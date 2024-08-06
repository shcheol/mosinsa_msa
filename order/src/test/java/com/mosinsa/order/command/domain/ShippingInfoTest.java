package com.mosinsa.order.command.domain;

import com.mosinsa.order.code.EqualsAndHashcodeUtils;
import com.mosinsa.order.code.TestClass;
import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ShippingInfoTest {

    @Test
    void equalsAndHashCode() {
		Address address = Address.of("zipCode", "address1", "address2");
		Receiver receiver = Receiver.of("myname", "010-xxx-xxxx");
        ShippingInfo shippingInfoA = ShippingInfo.of(address,receiver, "");
        ShippingInfo shippingInfoB = ShippingInfo.of(address,receiver, "");

        ShippingInfo shippingInfoC = ShippingInfo.of(address, Receiver.of("mynamexxx", "010-xxx-xxxx"), "myHome");
        ShippingInfo shippingInfoD = ShippingInfo.of(address, receiver, "myHomexxx");
        ShippingInfo shippingInfoE = ShippingInfo.of(Address.of("zipCodexxx", "address1", "address2"), receiver,"myHome");
		ShippingInfo protectedConstructor = new ShippingInfo();
		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(shippingInfoA, shippingInfoB, protectedConstructor, shippingInfoC, shippingInfoD, shippingInfoE);
		assertThat(b);
	}
}