package com.mosinsa.order.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.command.domain.OrderStatus;
import com.mosinsa.order.infra.feignclient.ExternalServerException;
import com.mosinsa.order.infra.feignclient.ResponseResult;
import com.mosinsa.order.infra.feignclient.coupon.CouponQueryService;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.infra.feignclient.customer.CustomerQueryService;
import com.mosinsa.order.infra.feignclient.customer.CustomerResponse;
import com.mosinsa.order.infra.feignclient.product.ProductQueryService;
import com.mosinsa.order.infra.feignclient.product.ProductResponse;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
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
    ProductQueryService productQueryService;

//	@MockBean
//	CouponCommandService couponCommandService;

//	@MockBean
//	ProductCommandService productCommandService;
//	@MockBean
//	PlaceOrderService placeOrderService;
//	@MockBean
//	CancelOrderService cancelOrderService;

	@Autowired
	ObjectMapper om;
	@Autowired
	OrderTemplate orderTemplate;
	OrderConfirmRequest orderConfirmRequestWithCoupon;
	OrderConfirmRequest orderConfirmRequestWithoutCoupon;

	@Test
	void orderConfirmSuccessWithCoupon() {

		when(customerQueryService.customerCheck(any(),any()))
				.thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
		when(productQueryService.productCheck(any(),any()))
				.thenReturn(ResponseResult.execute(() -> new ProductResponse("productId", "name",3000,10,2)));
		when(couponQueryService.couponCheck(any(),any()))
				.thenReturn(ResponseResult.execute(()->new CouponResponse("couponId","TEN_PERCENTAGE","promotionId", LocalDateTime.MIN, "customerId",LocalDateTime.MAX, true)));

		OrderConfirmDto orderConfirmDto = orderTemplate.orderConfirm(Map.of(), orderConfirmRequestWithCoupon);
        assertThat(orderConfirmDto.orderProducts()).hasSize(1);
        assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
        assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
        assertThat(orderConfirmDto.couponId()).isEqualTo("couponId");
        assertThat(orderConfirmDto.totalAmount()).isEqualTo(5400);
	}

    @Test
    void orderConfirmCustomerServiceError() {

        when(customerQueryService.customerCheck(any(),any()))
                .thenThrow(ExternalServerException.class);
        assertThrows(ExternalServerException.class, () -> orderTemplate.orderConfirm(Map.of(), orderConfirmRequestWithCoupon));
    }

    @Test
    void orderConfirmProductServiceError() {
        when(customerQueryService.customerCheck(any(),any()))
                .thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
        when(productQueryService.productCheck(any(),any()))
                .thenThrow(ExternalServerException.class);
        assertThrows(ExternalServerException.class, () -> orderTemplate.orderConfirm(Map.of(), orderConfirmRequestWithCoupon));
    }

    @Test
    void orderConfirmCouponServiceErrorOrderWithCoupon() {
        when(customerQueryService.customerCheck(any(),any()))
                .thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
        when(productQueryService.productCheck(any(),any()))
                .thenReturn(ResponseResult.execute(() -> new ProductResponse("productId", "name",3000,10,2)));
        when(couponQueryService.couponCheck(any(),any()))
                .thenThrow(ExternalServerException.class);
        assertThrows(ExternalServerException.class, () -> orderTemplate.orderConfirm(Map.of(), orderConfirmRequestWithCoupon));
    }

    @Test
    void orderConfirmCouponServiceErrorOrderWithoutCoupon() {
        when(customerQueryService.customerCheck(any(),any()))
                .thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
        when(productQueryService.productCheck(any(),any()))
                .thenReturn(ResponseResult.execute(() -> new ProductResponse("productId", "name",3000,10,2)));
        when(couponQueryService.couponCheck(any(),any()))
                .thenThrow(ExternalServerException.class);
        OrderConfirmDto orderConfirmDto = orderTemplate.orderConfirm(Map.of(), orderConfirmRequestWithoutCoupon);
        assertThat(orderConfirmDto.orderProducts()).hasSize(1);
        assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
        assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
        assertThat(orderConfirmDto.couponId()).isNull();
        assertThat(orderConfirmDto.totalAmount()).isEqualTo(6000);
    }

//	@Test
	void order() {
	}

//	@Test
	void cancelOrder() {
//		when(productCommandService.cancelOrderProduct(any(),any())).thenReturn(any());
//		when(couponCommandService.cancelCoupon(any(Map.class),eq(ResponseResult.class))).thenReturn(any());
		String orderId = "orderId1";
		OrderDetail canceledOrder = orderTemplate.cancelOrder(Map.of(), orderId);
		assertThat(canceledOrder.getOrderId()).isEqualTo(orderId);
		assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
	}

//	@Test
	void cancelOrderExternalApiFail() {
//		when(productCommandService.cancelOrderProduct(any(),any())).thenThrow(new RuntimeException());
//		when(couponCommandService.cancelCoupon(any(),any())).thenReturn(any());
		String orderId = "orderId1";
		OrderDetail canceledOrder = orderTemplate.cancelOrder(Map.of(), orderId);
		assertThat(canceledOrder.getOrderId()).isEqualTo(orderId);
		assertThat(canceledOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
	}

	@BeforeEach
	void init() throws JsonProcessingException {
		om = new ObjectMapper();
		String s = """
				{
				    "customerId":"customerId",
				    "couponId":"couponId",
				    "myOrderProducts":[
				        {
				            "productId":"productId",
				            "quantity":2
				        }
				        ],
				        "shippingInfo":{
				            "message":"home",
				            "address":{
				                "zipCode":"zipcode",
				                "address1":"address1",
				                "address2":"address2"
				            },
				            "receiver":{
				                "name":"myname",
				                "phoneNumber":"010-1111-1111"
				            }
				        }
				}
				""";
		orderConfirmRequestWithCoupon = om.readValue(s, OrderConfirmRequest.class);

        String noCoupon = """
				{
				    "customerId":"customerId",
				    "myOrderProducts":[
				        {
				            "productId":"productId",
				            "quantity":2
				        }
				        ],
				        "shippingInfo":{
				            "message":"home",
				            "address":{
				                "zipCode":"zipcode",
				                "address1":"address1",
				                "address2":"address2"
				            },
				            "receiver":{
				                "name":"myname",
				                "phoneNumber":"010-1111-1111"
				            }
				        }
				}
				""";
        orderConfirmRequestWithoutCoupon = om.readValue(noCoupon, OrderConfirmRequest.class);
	}
}