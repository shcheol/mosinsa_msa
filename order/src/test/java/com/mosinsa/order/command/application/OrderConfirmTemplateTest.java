package com.mosinsa.order.command.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.infra.feignclient.ExternalServerException;
import com.mosinsa.order.infra.feignclient.ResponseResult;
import com.mosinsa.order.infra.feignclient.coupon.CouponQueryService;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.infra.feignclient.customer.CustomerQueryService;
import com.mosinsa.order.infra.feignclient.customer.CustomerResponse;
import com.mosinsa.order.infra.feignclient.product.ProductQueryService;
import com.mosinsa.order.infra.feignclient.product.ProductResponse;
import com.mosinsa.order.query.application.OrderConfirmTemplate;
import com.mosinsa.order.ui.argumentresolver.CustomerInfo;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderConfirmTemplateTest {

	@MockBean
	CustomerQueryService customerQueryService;
	@MockBean
	CouponQueryService couponQueryService;
	@MockBean
	ProductQueryService productQueryService;
	ObjectMapper om;
	@Autowired
	OrderConfirmTemplate orderConfirmTemplate;
	OrderConfirmRequest orderConfirmRequestWithCoupon;
	OrderConfirmRequest orderConfirmRequestWithoutCoupon;

	CustomerInfo customerInfo = new CustomerInfo("customerId", "name");
	Map<String, Collection<String>> header = Map.of();


	@Test
	void orderConfirmSuccessWithCoupon() {

		when(customerQueryService.customerCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
		when(productQueryService.productCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new ProductResponse("productId", "name", 3000, 10, 2)));
		when(couponQueryService.couponCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new CouponResponse("couponId", "TEN_PERCENTAGE", "promotionId", LocalDateTime.MIN, "customerId", LocalDateTime.MAX, true)));


		OrderConfirmDto orderConfirmDto = orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithCoupon);
		assertThat(orderConfirmDto.orderProducts()).hasSize(1);
		assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
		assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
		assertThat(orderConfirmDto.couponId()).isEqualTo("couponId");
		assertThat(orderConfirmDto.totalAmount()).isEqualTo(5400);
	}

	@Test
	void orderConfirmNotEnoughStock() {

		when(customerQueryService.customerCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
		when(productQueryService.productCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new ProductResponse("productId", "name", 3000, 1, 2)));
		when(couponQueryService.couponCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new CouponResponse("couponId", "TEN_PERCENTAGE", "promotionId", LocalDateTime.MIN, "customerId", LocalDateTime.MAX, true)));

		assertThrows(NotEnoughProductStockException.class, () -> orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithCoupon));
	}

	@Test
	void orderConfirmCustomerServiceError() {

		when(customerQueryService.customerCheck(any(), any()))
				.thenThrow(ExternalServerException.class);
		assertThrows(ExternalServerException.class, () -> orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithCoupon));
	}

	@Test
	void orderConfirmProductServiceError() {
		when(customerQueryService.customerCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
		when(productQueryService.productCheck(any(), any()))
				.thenThrow(ExternalServerException.class);
		assertThrows(ExternalServerException.class, () -> orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithCoupon));
	}

	@Test
	void orderConfirmCouponServiceErrorOrderWithCoupon() {
		when(customerQueryService.customerCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
		when(productQueryService.productCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new ProductResponse("productId", "name", 3000, 10, 2)));
		when(couponQueryService.couponCheck(any(), any()))
				.thenThrow(ExternalServerException.class);
		assertThrows(ExternalServerException.class, () -> orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithCoupon));
	}

	@Test
	void orderConfirmCouponServiceErrorOrderWithoutCoupon() {
		when(customerQueryService.customerCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new CustomerResponse("customerId", "name")));
		when(productQueryService.productCheck(any(), any()))
				.thenReturn(ResponseResult.execute(() -> new ProductResponse("productId", "name", 3000, 10, 2)));
		when(couponQueryService.couponCheck(any(), any()))
				.thenThrow(ExternalServerException.class);
		OrderConfirmDto orderConfirmDto = orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithoutCoupon);
		assertThat(orderConfirmDto.orderProducts()).hasSize(1);
		assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
		assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
		assertThat(orderConfirmDto.couponId()).isNull();
		assertThat(orderConfirmDto.totalAmount()).isEqualTo(6000);
	}

	@BeforeEach
	void init() throws JsonProcessingException {
		om = new ObjectMapper();
		String s = """
				{
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