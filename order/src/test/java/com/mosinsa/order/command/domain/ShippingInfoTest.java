package com.mosinsa.order.command.domain;

import com.mosinsa.order.code.EqualsAndHashcodeUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingInfoTest {

    @Test
    void equalsAndHashCode() {
		Address address = Address.of("zipCode", "address1", "address2");
		Receiver receiver = Receiver.of("myname", "010-xxx-xxxx");
		String message = "myHome";
		ShippingInfo shippingInfoA = ShippingInfo.of(address,receiver, message);
        ShippingInfo shippingInfoB = ShippingInfo.of(address,receiver, message);

		ShippingInfo shippingInfoE = ShippingInfo.of(Address.of("zipCodexxx", "address1", "address2"), receiver, message);
		ShippingInfo shippingInfoC = ShippingInfo.of(address, Receiver.of("mynamexxx", "010-xxx-xxxx"), message);
		ShippingInfo shippingInfoD = ShippingInfo.of(address, receiver, "myHomexxx");

		ShippingInfo protectedConstructor = new ShippingInfo();
		boolean b = EqualsAndHashcodeUtils.equalsAndHashcode(shippingInfoA, shippingInfoB, protectedConstructor, shippingInfoC, shippingInfoD, shippingInfoE);
		assertThat(b).isTrue();
	}
}