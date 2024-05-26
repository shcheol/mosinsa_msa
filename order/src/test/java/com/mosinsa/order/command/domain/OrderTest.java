package com.mosinsa.order.command.domain;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.common.ex.OrderException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    private final ShippingInfoDto shippingInfoDto = new ShippingInfoDto("", new AddressDto("", "", ""), new ReceiverDto("", ""));


    @Test
    void create_상품_empty() {
        List<OrderProduct> orderProducts = List.of();
		ShippingInfo shippingInfo = ShippingInfo.of(shippingInfoDto);
		assertThrows(OrderException.class,
                () -> Order.create("customerId", "couponId", orderProducts,
                        shippingInfo, 10000));
    }

	@Test
	void create_상품_null() {
		List<OrderProduct> orderProducts = null;
		ShippingInfo shippingInfo = ShippingInfo.of(shippingInfoDto);
		assertThrows(OrderException.class,
				() -> Order.create("customerId", "couponId", orderProducts,
						shippingInfo, 10000));
	}

    @Test
    void create_주문자x() {
        List<OrderProduct> orderProducts = List.of(OrderProduct.create("id", 1000, 1));
		ShippingInfo shippingInfo = ShippingInfo.of(shippingInfoDto);
        assertThrows(OrderException.class,
				() -> Order.create("", "couponId", orderProducts,
						shippingInfo, 10000));
    }



//    @Test
//    void 쿠폰사용() {
//        List<OrderProduct> orderProducts = List.of(OrderProduct.create("id", 1000, 1));
//        Order order = Order.create("customerId", "couponId", orderProducts,
//				ShippingInfo.of(shippingInfoDto), 10000);
////        order.useCoupon("couponId", "TEN_PERCENTAGE");
//        assertThat(order.getTotalPrice()).isEqualTo(Money.of(900));
//    }
//    @Test
//    void 쿠폰사용_noId() {
//        List<OrderProduct> orderProducts = List.of(OrderProduct.create("id", 1000, 1));
//        Order order = Order.create("customerId", orderProducts,
//                ShippingInfo.of(shippingInfoDto));
//
//        assertThrows(InvalidCouponException.class,
//                () -> order.useCoupon("", "TEN_PERCENTAGE"));
//    }
//
//    @Test
//    void 쿠폰사용_invalid_discountPolicy() {
//        List<OrderProduct> orderProducts = List.of(OrderProduct.create("id", 1000, 1));
//        Order order = Order.create("customerId", orderProducts,
//                ShippingInfo.of(shippingInfoDto));
//
//        assertThrows(IllegalArgumentException.class,
//                () -> order.useCoupon("asdf", "TEN_PERCENTAGExxxxx"));
//    }
}