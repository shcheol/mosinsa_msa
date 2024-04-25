package com.mosinsa.order.command.application;

import com.mosinsa.order.command.application.dto.AddressDto;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.application.dto.ReceiverDto;
import com.mosinsa.order.command.application.dto.ShippingInfoDto;
import com.mosinsa.order.command.domain.OrderStatus;
import com.mosinsa.order.command.domain.ShippingInfo;
import com.mosinsa.order.infra.feignclient.ResponseResult;
import com.mosinsa.order.infra.feignclient.coupon.CouponCommandService;
import com.mosinsa.order.infra.feignclient.coupon.CouponQueryService;
import com.mosinsa.order.infra.feignclient.customer.CustomerQueryService;
import com.mosinsa.order.infra.feignclient.product.ProductCommandService;
import com.mosinsa.order.infra.feignclient.product.ProductQueryService;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.MyOrderProduct;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderTemplateTest {

	@MockBean
	CustomerQueryService customerQueryService;
	@MockBean
	CouponQueryService couponQueryService;
	@MockBean
	CouponCommandService couponCommandService;
	@MockBean
	ProductQueryService productQueryService;
	@MockBean
	ProductCommandService productCommandService;
	@MockBean
	PlaceOrderService placeOrderService;
	@MockBean
	CancelOrderService cancelOrderService;

	@Autowired
	OrderTemplate orderTemplate;

//	@Test
	void orderConfirm() {
		ShippingInfoDto shippingInfoDto = new ShippingInfoDto("", new AddressDto("", "", ""), new ReceiverDto("", ""));
		OrderConfirmRequest orderConfirmRequest = new OrderConfirmRequest("customerId","couponId", shippingInfoDto,
				List.of(new MyOrderProduct("productId1", 1)));

		when(customerQueryService.customerCheck(any(),any())).thenReturn(any());
		when(customerQueryService.customerCheck(any(),any())).thenReturn(any());

		OrderConfirmDto orderConfirmDto = orderTemplate.orderConfirm(Map.of(), orderConfirmRequest);

	}

//	@Test
	void order() {
	}

//	@Test
	void cancelOrder() {
		when(productCommandService.cancelOrderProduct(any(),any())).thenReturn(any());
//		when(couponCommandService.cancelCoupon(any(Map.class),eq(ResponseResult.class))).thenReturn(any());
		String orderId = "orderId1";
		OrderDetail canceledOrder = orderTemplate.cancelOrder(Map.of(), orderId);
		assertThat(canceledOrder.getOrderId()).isEqualTo(orderId);
		assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
	}

//	@Test
	void cancelOrderExternalApiFail() {
		when(productCommandService.cancelOrderProduct(any(),any())).thenThrow(new RuntimeException());
		when(couponCommandService.cancelCoupon(any(),any())).thenReturn(any());
		String orderId = "orderId1";
		OrderDetail canceledOrder = orderTemplate.cancelOrder(Map.of(), orderId);
		assertThat(canceledOrder.getOrderId()).isEqualTo(orderId);
		assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
	}
}