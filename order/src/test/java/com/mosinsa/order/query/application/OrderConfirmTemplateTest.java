package com.mosinsa.order.query.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.order.command.application.dto.OrderConfirmDto;
import com.mosinsa.order.common.argumentresolver.CustomerInfo;
import com.mosinsa.order.ui.request.OrderConfirmRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({OrderQueryObjectFactory.class})
class OrderConfirmTemplateTest {

	ObjectMapper om;
	@Autowired
	OrderConfirmTemplate orderConfirmTemplate;
	OrderConfirmRequest orderConfirmRequestWithCoupon;
	OrderConfirmRequest orderConfirmRequestWithoutCoupon;
	CustomerInfo customerInfo = new CustomerInfo("customerId", "name");
	Map<String, Collection<String>> header = Map.of();


	@Test
	void orderConfirmSuccessWithCoupon() {

		OrderConfirmDto orderConfirmDto = orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithCoupon);
		assertThat(orderConfirmDto.orderProducts()).hasSize(1);
		assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
		assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
		assertThat(orderConfirmDto.couponId()).isEqualTo("couponId");
		assertThat(orderConfirmDto.totalAmount()).isEqualTo(1800);
	}

	@Test
	void orderConfirmCouponServiceErrorOrderWithoutCoupon() {

		OrderConfirmDto orderConfirmDto = orderConfirmTemplate.orderConfirm(header, customerInfo, orderConfirmRequestWithoutCoupon);
		assertThat(orderConfirmDto.orderProducts()).hasSize(1);
		assertThat(orderConfirmDto.customerId()).isEqualTo("customerId");
		assertThat(orderConfirmDto.shippingInfo()).isEqualTo(orderConfirmRequestWithCoupon.shippingInfo());
		assertThat(orderConfirmDto.couponId()).isNull();
		assertThat(orderConfirmDto.totalAmount()).isEqualTo(2000);
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