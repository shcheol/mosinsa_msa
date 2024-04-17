package com.mosinsa.order.domain;

import com.mosinsa.order.command.domain.*;
import com.mosinsa.order.common.ex.OrderException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    void create_상품x() {
        List<OrderProduct> orderProducts = List.of();
        assertThrows(OrderException.class,
                () -> Order.create("customerId", orderProducts,
                        ShippingInfo.of(null, "", null)));
    }

    @Test
    void create_주문자x() {
        List<OrderProduct> orderProducts = List.of();
        assertThrows(OrderException.class,
                () -> Order.create("", orderProducts,
                        ShippingInfo.of(null, "", null)));
    }

    @Test
    void 쿠폰사용() {
        List<OrderProduct> orderProducts = List.of(OrderProduct.create("id", 1000, 1));
        Order order = Order.create("customerId", orderProducts,
                ShippingInfo.of(null, "", null));
        order.useCoupon("couponId", "TEN_PERCENTAGE");
        assertThat(order.getTotalPrice()).isEqualTo(Money.of(900));
    }
    @Test
    void 쿠폰사용_noId() {
        List<OrderProduct> orderProducts = List.of(OrderProduct.create("id", 1000, 1));
        Order order = Order.create("customerId", orderProducts,
                ShippingInfo.of(null, "", null));

        assertThrows(InvalidCouponException.class,
                () -> order.useCoupon("", "TEN_PERCENTAGE"));
    }

    @Test
    void 쿠폰사용_invalid_discountPolicy() {
        List<OrderProduct> orderProducts = List.of(OrderProduct.create("id", 1000, 1));
        Order order = Order.create("customerId", orderProducts,
                ShippingInfo.of(null, "", null));

        assertThrows(IllegalArgumentException.class,
                () -> order.useCoupon("asdf", "TEN_PERCENTAGExxxxx"));
    }
}