package com.mosinsa.order.command.domain;

import com.mosinsa.order.common.ex.OrderException;
import com.mosinsa.order.infra.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql("classpath:db/test-init.sql")
class OrderTest {

	@Autowired
	OrderRepository repository;
	private final Receiver receiver = Receiver.of("", "");
	private final Address address = Address.of("", "","");

	@Test
	void equalsAndHashCode() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", 1000, 1));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver,"");
		Order order1 = Order.create("cId", "couponId", orderProducts, shippingInfo, 10000);

		Order protectedConstructor = new Order();
		assertThat(protectedConstructor).isNotEqualTo(order1);
	}

	@Test
	void create_상품_empty() {
		List<OrderProduct> orderProducts = List.of();
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver,"");
		assertThrows(OrderException.class,
				() -> Order.create("customerId", "couponId", orderProducts,
						shippingInfo, 10000));
	}

	@Test
	void create_상품_null() {
		List<OrderProduct> orderProducts = null;
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver,"");
		assertThrows(OrderException.class,
				() -> Order.create("customerId", "couponId", orderProducts,
						shippingInfo, 10000));
	}

	@Test
	void create_주문자x() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", 1000, 1));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver,"");
		assertThrows(OrderException.class,
				() -> Order.create("", "couponId", orderProducts,
						shippingInfo, 10000));
	}


	@Test
	void cancelOrder() {
		List<OrderProduct> orderProducts = List.of(OrderProduct.of("id", 1000, 1));
		ShippingInfo shippingInfo = ShippingInfo.of(address, receiver,"");
		Order order = Order.create("cId", "couponId", orderProducts, shippingInfo, 10000);
		order.cancelOrder();
		assertThrows(AlreadyCanceledException.class, order::cancelOrder);

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